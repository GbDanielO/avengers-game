package br.com.avengers.adapters.in.messaging;

import br.com.avengers.ports.in.DLQMessagePortIn;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * Tratar mensagens com erro
 * autoStartup = "false" deixa o listener parado (não inicia na subida da aplicação)
 * O Spring Kafka mantém todos os listeners registrados internamente de forma que podemos
 * controlá-lo por API: Start, Stop, ver Status... pra isso ele precisa de um id: id = "dlqBatalhaListener",
 *
 */
@Log4j2
@Component
public class DLQMessageAdapterIn {

    private final DLQMessagePortIn dlqMessagePortIn;

    @Autowired
    public DLQMessageAdapterIn(DLQMessagePortIn dlqMessagePortIn) {
        this.dlqMessagePortIn = dlqMessagePortIn;
    }

    /**
     * Aqui o consumer recebe o tipo da mensagem como String justamente porque é um debug/correção, não faz
     * sentido tentar trazer como um objeto
     * @param mensagem
     */
    @KafkaListener(
            id = "dlqBatalhaListener",
            topics = "batalha.DLQ",
            groupId = "ms-batalha-dlq",
            containerFactory = "dlqKafkaListenerContainerFactory",
            autoStartup = "true"
    )
    public void consumirDlq(ConsumerRecord<String, byte[]> mensagem) {

        String traceId = pegaTraceId(mensagem);

        try (var ctx = CloseableThreadContext.put("traceId", traceId)) {
            dlqMessagePortIn.processaMensagemDLQ(traceId, mensagem);
        } catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }

    public String pegaTraceId(ConsumerRecord<String, byte[]> mensagem){
        Header header = mensagem.headers().lastHeader("X-Trace-Id");

        if (header != null) {
            return new String(header.value(), StandardCharsets.UTF_8);
        }
        return "SEM-TRACE-ID";
    }
}
