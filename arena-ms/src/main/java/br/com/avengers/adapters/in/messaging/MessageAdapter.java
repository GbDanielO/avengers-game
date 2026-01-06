package br.com.avengers.adapters.in.messaging;

import br.com.avengers.adapters.dto.BatalhaDTO;
import br.com.avengers.domain.model.Batalha;
import br.com.avengers.ports.in.MessagePort;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Log4j2
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

        String traceId = pegaTraceId(mensagem);

        try {
            log.info("Recebendo batalha na Arena");
            BatalhaDTO batalhaDTO = objectMapper.convertValue(mensagem.value(), BatalhaDTO.class);
            messagePort.processaBatalha(Batalha.getInstanceFrom(batalhaDTO), traceId);
        } finally {
            ThreadContext.clearAll();
        }
    }

    public String pegaTraceId(ConsumerRecord<String, Object> mensagem){
        Header header = mensagem.headers().lastHeader("X-Trace-Id");

        if (header != null) {
            return new String(header.value(), StandardCharsets.UTF_8);
        }
        return null;
    }
}
