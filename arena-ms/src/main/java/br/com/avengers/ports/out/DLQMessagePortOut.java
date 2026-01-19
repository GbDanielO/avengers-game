package br.com.avengers.ports.out;

import java.util.Map;

public interface DLQMessagePortOut {
    void redriveAutomatico(byte[] payload, Map<String, Object> headers);
    void redriveManual(String jsonCorrigido, Map<String, Object> headers);
}
