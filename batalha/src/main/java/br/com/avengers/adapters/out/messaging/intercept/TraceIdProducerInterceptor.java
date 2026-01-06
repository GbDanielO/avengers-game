package br.com.avengers.adapters.out.messaging.intercept;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.logging.log4j.ThreadContext;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class TraceIdProducerInterceptor implements ProducerInterceptor<Object, Object> {

    private static final String HEADER = "X-Trace-Id";

    @Override
    public ProducerRecord<Object, Object> onSend(ProducerRecord<Object, Object> record) {

        String traceId = ThreadContext.get("traceId");

        if (traceId != null) {
            record.headers().add(
                    HEADER,
                    traceId.getBytes(StandardCharsets.UTF_8)
            );
        }

        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {
        // nada aqui
    }

    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> configs) {}
}
