package br.com.avengers.adapters.out.messaging;

import br.com.avengers.ports.out.DLQMessagePortOut;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Esse cara é o ponto final, que joga a mensagem novamente para a fila original
 */
@Component
public class DLQMessageAdapterOut implements DLQMessagePortOut {

    private final KafkaTemplate<String, byte[]> kafkaTemplateAutomatico;
    private final KafkaTemplate<String, Object> kafkaTemplateManual;

    @Autowired
    public DLQMessageAdapterOut(
           @Qualifier("dlqRedriveAutomaticoKafkaTemplate") KafkaTemplate<String, byte[]> kafkaTemplateAutomatico,
           @Qualifier("dlqRedriveManualKafkaTemplate") KafkaTemplate<String, Object> kafkaTemplateManual) {
        this.kafkaTemplateAutomatico = kafkaTemplateAutomatico;
        this.kafkaTemplateManual = kafkaTemplateManual;
    }

    @Override
    public void redriveAutomatico(byte[] payload, Map<String, Object> headers) {
        ProducerRecord<String, byte[]> mensagem =
                new ProducerRecord<>("batalha", payload);

        headers.forEach((key, value) -> {
            if (!key.startsWith("x-exception") && value instanceof byte[] bytes) {
                mensagem.headers().add(key, bytes);
            }
        });

        kafkaTemplateAutomatico.send(mensagem);
    }

    @Override
    public void redriveManual(String jsonCorrigido, Map<String, Object> headers) {
        ProducerRecord<String, Object> mensagem =
                new ProducerRecord<>("batalha", jsonCorrigido);

        headers.forEach((key, value) -> {
            if (value instanceof byte[] bytes) {
                mensagem.headers().add(key, bytes);
            }
        });

        kafkaTemplateManual.send(mensagem);
    }
}
