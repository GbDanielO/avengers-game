package br.com.avengers.ports.in;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface DLQMessagePortIn {

    public void processaMensagemDLQ(String traceId, ConsumerRecord<String, byte[]> mensagem);

    void redriveManual(String id, String jsonCorrigido);
}
