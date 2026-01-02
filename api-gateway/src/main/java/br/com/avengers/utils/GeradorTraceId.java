package br.com.avengers.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GeradorTraceId {

    private static final String PREFIXO = "AVENGERSGAME";
    private static final int TAMANHO_RANDOMICO = 10;
    private static final String CARACTERES = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static String gerarTraceID() {
        String dataHora = LocalDateTime.now().format(FORMATTER);
        String randomico = gerarStringRandomica(TAMANHO_RANDOMICO);

        return PREFIXO + dataHora + randomico;
    }

    private static String gerarStringRandomica(int tamanho) {
        StringBuilder sb = new StringBuilder(tamanho);
        for (int i = 0; i < tamanho; i++) {
            int index = RANDOM.nextInt(CARACTERES.length());
            sb.append(CARACTERES.charAt(index));
        }
        return sb.toString();
    }

}
