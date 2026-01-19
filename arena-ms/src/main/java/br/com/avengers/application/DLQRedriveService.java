package br.com.avengers.application;

import br.com.avengers.ports.in.DLQMessagePortIn;
import br.com.avengers.ports.out.DLQMessagePortOut;
import br.com.avengers.ports.out.DLQRepositoryPort;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Service
public class DLQRedriveService implements DLQMessagePortIn {

    private final DLQMessagePortOut dlqMessagePortOut;
    private final DLQRepositoryPort dlqRepositoryPort;

    @Autowired
    public DLQRedriveService(DLQMessagePortOut dlqMessagePortOut, DLQRepositoryPort dlqRepositoryPort) {
        this.dlqMessagePortOut = dlqMessagePortOut;
        this.dlqRepositoryPort = dlqRepositoryPort;
    }

    @Override
    public void processaMensagemDLQ(String traceId, ConsumerRecord<String, byte[]> mensagem) {

        ThreadContext.put("traceId", traceId);
        try {
            // Preenche mensagem para auditoria
            DLQMessageDTO entity = DLQMessageDTO.from(mensagem);

            // Persistir mensagem para auditoria
            dlqRepositoryPort.save(entity);

            if (isErroRecuperavel(entity.extrairClasseErro(entity.getHeaders()))) {
                // Tentativa automática de reenvio
                try {
                    dlqMessagePortOut.redriveAutomatico(
                            entity.getPayloadRaw(),
                            formataHeadersParaArrayBytes(entity.getHeaders())
                    );

                    entity.setStatus(DlqStatusEnum.REDRIVEN_SUCESSO);
                } catch (Exception ex) {
                    entity.setStatus(DlqStatusEnum.REDRIVEN_FALHA);
                }
            } else {
                entity.setStatus(DlqStatusEnum.REDRIVEN_FALHA);
            }
            dlqRepositoryPort.save(entity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void redriveManual(String id, String jsonCorrigido) {
        try (var ctx = CloseableThreadContext.put("traceId", id)) {
            DLQMessageDTO entity = dlqRepositoryPort.findByTraceId(id)
                    .orElseThrow();

            dlqMessagePortOut.redriveManual(
                    jsonCorrigido,
                    formataHeadersParaArrayBytes(entity.getHeaders())
            );

            entity.setStatus(DlqStatusEnum.MANUAL);
            dlqRepositoryPort.save(entity);
        }

    }

    public Map<String, Object> formataHeadersParaArrayBytes(Map<String, String> headers){
        Map<String, Object> headersFormatados = new HashMap<>();
        headers.forEach((key, value) -> {
            // Converte a String para byte[] usando UTF-8
            headersFormatados.put(key, value.getBytes(StandardCharsets.UTF_8));
        });
        return headersFormatados;
    }

    private boolean isErroRecuperavel(String erro) {
        // Se o erro contém "DeserializationException", não adianta tentar re-drive sem correção manual
        return erro == null ||
                (!erro.contains("DeserializationException")
                && !erro.contains("JsonParseException")
                && !erro.contains("JsonMappingException") );
    }
}
