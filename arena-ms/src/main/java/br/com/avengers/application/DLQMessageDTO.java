package br.com.avengers.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.Header;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "batalha-dlq")
public class DLQMessageDTO {

    @Id
    private String id;

    // Kafka metadata
    private String originalTopic;
    private String dlqTopic;
    private Integer partition;
    private Long offset;

    // Payload
    private byte[] payloadRaw;        // exatamente o que veio do Kafka
    private String payloadFormatado;     // JSON formatado (se possível)

    // Headers relevantes
    @Indexed(unique = true)
    private String traceId;
    private Map<String, String> headers;

    // Erro
    private String errorMessage;
    private String stackTrace;

    // Controle
    private DlqStatusEnum status; // PENDING | REDRIVEN | FAILED | MANUAL
    private Integer attempts;

    // Auditoria
    private Instant createdAt;
    private Instant lastAttemptAt;

    // Factory method
    public static DLQMessageDTO from(
            ConsumerRecord<String, byte[]> mensagem
    ) {
        byte[] raw = mensagem.value();
        Map<String, String> headers = extrairHeaders(mensagem);

        return DLQMessageDTO.builder()
                .originalTopic(mensagem.topic())
                .dlqTopic("batalha.DLQ")
                .partition(mensagem.partition())
                .offset(mensagem.offset())
                .payloadRaw(raw)
                .payloadFormatado(formatar(raw))
                .traceId(pegaTraceId(headers))
                .headers(headers)
                // Captura a exceção enviada pelo DeadLetterPublishingRecoverer
                .errorMessage(headers.get("kafka_dlt-exception-message"))
                .stackTrace(headers.get("kafka_dlt-exception-stacktrace"))
                .status(DlqStatusEnum.PENDENTE)
                .attempts(0)
                .createdAt(Instant.now())
                .build();
    }

    private static String formatar(byte[] raw) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Object json = mapper.readValue(raw, Object.class);
            return mapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(json);
        } catch (Exception e) {
            try {
                // Se não for JSON, tenta como String UTF-8
                String valor = new String(raw, StandardCharsets.UTF_8);

                // testa se é base64
                if (isBase64(valor)) {
                    byte[] decoded = Base64.getDecoder().decode(valor);
                    return new String(decoded, StandardCharsets.UTF_8);
                }

                return valor;

            } catch (Exception encodingException) {
                // Último fallback: binário
                return "<payload binário não legível>";
            }
        }
    }

    public static Map<String, String> extrairHeaders(
            ConsumerRecord<?, ?> mensagem
    ) {
        Map<String, String> headersMap = new HashMap<>();

        for (Header header : mensagem.headers()) {
            if (header.value() != null) {
                headersMap.put(
                        header.key(),
                        new String(header.value(), StandardCharsets.UTF_8)
                );
            }
        }

        return headersMap;
    }

    public String extrairClasseErro(Map<String, String> headers) {

        if (headers == null || headers.isEmpty()) {
            return null;
        }

        if (headers.containsKey("kafka_dlt-exception-fqcn")) {
            return headers.get("kafka_dlt-exception-fqcn");
        }

        return null;
    }

    private static String pegaTraceId(Map<String, String> headers) {
        return headers.getOrDefault("X-Trace-Id", "SEM-TRACE-ID");
    }

    private static boolean isBase64(String value) {
        try {
            Base64.getDecoder().decode(value);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
