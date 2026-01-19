package br.com.avengers.config;

import br.com.avengers.shared.NegocioException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.DeserializationException;
import org.springframework.util.backoff.ExponentialBackOff;

import java.util.Map;

@Configuration
public class KafkaErrorHandlerConfig {

    @Bean(name = "errorHandler")
    public DefaultErrorHandler errorHandler(
            @Qualifier("dlqKafkaTemplate")
            KafkaTemplate<?, ?> dlqKafkaTemplate
    ) {

        DeadLetterPublishingRecoverer recoverer =
            new DeadLetterPublishingRecoverer( dlqKafkaTemplate,
                (record, ex) ->
                    new TopicPartition(
                        record.topic() + ".DLQ",
                        record.partition()
                    )
            ){
                protected Object createValue(ConsumerRecord<?, ?> record, Exception ex) {
                    return Map.of(
                            "topic", record.topic(),
                            "partition", record.partition(),
                            "offset", record.offset(),
                            "traceId", record.headers().lastHeader("X-Trace-Id") != null
                                    ? new String(record.headers().lastHeader("X-Trace-Id").value())
                                    : "SEM-TRACE-ID",
                            "exception", ex.getClass().getSimpleName(),
                            "message", ex.getMessage()
                    );
                }

            };

        // Retry: 3 tentativas, backoff exponencial
        ExponentialBackOff backOff = new ExponentialBackOff();
        backOff.setInitialInterval(3_000); // 3s
        backOff.setMultiplier(2.0);
        backOff.setMaxInterval(15_000);
        backOff.setMaxElapsedTime(20_000);

        DefaultErrorHandler handler =
            new DefaultErrorHandler(recoverer, backOff);

        // Exceções que NÃO devem ser retry
        handler.addNotRetryableExceptions(
            IllegalArgumentException.class,
            NullPointerException.class,
            DeserializationException.class
        );

        return handler;
    }
}
