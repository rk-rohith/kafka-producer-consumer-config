package com.example.kafka.producer.consumer.service;

import com.example.kafka.producer.consumer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafKaConsumerService {

    @Value(value = "${user.topic.group.id}")
    private String userGroupId;


    @KafkaListener(topics = "${general.topic.name}",
            groupId = "${general.topic.group.id}")
    public void consume(String message) {
        log.info(String.format("Message recieved -> %s", message));
    }

    @KafkaListener(topics = "${user.topic.name}",
            groupId = "${user.topic.group.id}",
            containerFactory = "userKafkaListenerContainerFactory")
    public void consumer(User user) {
        log.info("groupId -> {}", userGroupId);
        log.info("User created -> {}", user);
    }


}
