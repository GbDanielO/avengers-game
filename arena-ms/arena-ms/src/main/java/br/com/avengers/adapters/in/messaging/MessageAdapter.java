package br.com.avengers.adapters.in.messaging;

import br.com.avengers.adapters.dto.BatalhaDTO;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.in.MessagePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageAdapter {

    private final MessagePort messagePort;
    private final ObjectMapper objectMapper;

    @Autowired
    public MessageAdapter(MessagePort messagePort, ObjectMapper objectMapper) {
        this.messagePort = messagePort;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "batalha", groupId = "ms-batalha", containerFactory = "kafkaListenerContainerFactory")
    public void processaBatalha(ConsumerRecord<String, Object> mensagem){
        BatalhaDTO batalhaDTO = objectMapper.convertValue(mensagem.value(), BatalhaDTO.class);
        messagePort.processaBatalha(Batalha.getInstanceFrom(batalhaDTO));
    }
}
