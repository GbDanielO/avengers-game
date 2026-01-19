package br.com.avengers.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaDLQProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

//##############################
//   Producer DLQ erro (usado no ErroHandler)
//##############################
    @Bean
    public ProducerFactory<String, Object> dlqProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 2097152);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "dlqKafkaTemplate")
    public KafkaTemplate<String, Object> dlqKafkaTemplate() {
        return new KafkaTemplate<>(dlqProducerFactory());
    }

//##############################
//   Producer Redrive Automatico
//##############################
    @Bean
    public ProducerFactory<String, byte[]> dlqRedriveAutomaticoProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 2097152);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.ByteArraySerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "dlqRedriveAutomaticoKafkaTemplate")
    public KafkaTemplate<String, byte[]> dlqRedriveAutomaticoKafkaTemplate() {
        return new KafkaTemplate<>(dlqRedriveAutomaticoProducerFactory());
    }

//##############################
//   Producer Redrive Manual
//##############################
    @Bean
    public ProducerFactory<String, Object> dlqRedriveManualProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);

        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 2097152); //2Mb
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                org.apache.kafka.common.serialization.StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean(name = "dlqRedriveManualKafkaTemplate")
    public KafkaTemplate<String, Object> dlqRedriveManualKafkaTemplate() {
        return new KafkaTemplate<>(dlqRedriveManualProducerFactory());
    }
}
