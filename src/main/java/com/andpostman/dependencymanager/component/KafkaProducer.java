package com.andpostman.dependencymanager.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import com.andpostman.dependencymanager.model.Subscriber;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaProducer {

    @Value("${topic.name}")
    private String eventTopic;

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMessage(List<Subscriber> subscribers){
        String eventAsMessage = null;
        try {
            eventAsMessage = objectMapper.writeValueAsString(subscribers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        kafkaTemplate.send(eventTopic, eventAsMessage);
        log.info("event produced {}", eventAsMessage);

        return "message sent Subscribers=" + subscribers.size() + '\n' + subscribers;
    }
}
