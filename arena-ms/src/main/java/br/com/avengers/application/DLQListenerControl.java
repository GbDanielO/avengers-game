package br.com.avengers.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.stereotype.Component;

@Component
public class DLQListenerControl {

    private final KafkaListenerEndpointRegistry registry;

    @Autowired
    public DLQListenerControl(KafkaListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    public void startDlqListener() {
        registry.getListenerContainer("dlqBatalhaListener").start();
    }

    public void stopDlqListener() {
        registry.getListenerContainer("dlqBatalhaListener").stop();
    }

    public boolean isRunning() {
        return registry.getListenerContainer("dlqBatalhaListener").isRunning();
    }
}
