package br.com.avengers.adapters.out.messaging;

import br.com.avengers.adapters.dto.BatalhaDTO;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.out.MessagePort;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageAdapter implements MessagePort {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public MessageAdapter(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void enviarMensagem(String topico, Batalha batalha) {
        kafkaTemplate.send(topico, BatalhaDTO.getInstanceFrom(batalha));
    }
}
