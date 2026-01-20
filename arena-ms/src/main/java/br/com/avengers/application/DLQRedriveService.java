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

    private static final String DLQ_REDRIVE_MANUAL_ATTEMPT = "X-dlq-redrive-manual-attempt";
    public static final String DLQ_REDRIVE_AUTO_ATTEMPT = "X-dlq-redrive-auto-attempt";

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

            if (deveTentarReenviar(entity.getHeaders(), true)
                    && isErroRecuperavel(entity.extrairClasseErro(entity.getHeaders()))) {

                // Tentativa automática de reenvio
                try {
                    dlqMessagePortOut.redriveAutomatico(
                            entity.getPayloadRaw(),
                            formataHeadersParaArrayBytes(montarHeaders(entity.getHeaders(), true))
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

            if(deveTentarReenviar(entity.getHeaders(), false)) {
                dlqMessagePortOut.redriveManual(
                        jsonCorrigido,
                        formataHeadersParaArrayBytes(montarHeaders(entity.getHeaders(), false))
                );
                entity.setStatus(DlqStatusEnum.MANUAL);
                dlqRepositoryPort.save(entity);
            } else {
                entity.setStatus(DlqStatusEnum.MANUAL_FALHA);
            }
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

    private Map<String, String> montarHeaders(Map<String, String> headers, boolean auto) {

        Map<String, String> novosHeaders = new HashMap<>();
        // headers que vieram na mensagem e preciso reenviar (varia de acordo com a regra de negócio)
        headers.forEach((key, value) -> {
            if (key.equals("X-Trace-Id")
                    || key.equals(DLQ_REDRIVE_AUTO_ATTEMPT)
                    || key.equals(DLQ_REDRIVE_MANUAL_ATTEMPT)) {
                novosHeaders.put(key, value);
            }
        });
        // adiciono que já passou pela DLQ
        novosHeaders.put(auto ? DLQ_REDRIVE_AUTO_ATTEMPT : DLQ_REDRIVE_MANUAL_ATTEMPT, "1");

        return novosHeaders;
    }

    private boolean deveTentarReenviar(Map<String, String> headers, boolean auto){

        if(headers.containsKey(DLQ_REDRIVE_AUTO_ATTEMPT) && auto) {
            return false;
        }
        if(headers.containsKey(DLQ_REDRIVE_MANUAL_ATTEMPT) && !auto){
            return false;
        }
        return true;
    }
}
