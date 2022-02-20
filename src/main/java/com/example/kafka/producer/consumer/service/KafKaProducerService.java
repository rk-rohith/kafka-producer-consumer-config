package com.example.kafka.producer.consumer.service;

import com.example.kafka.producer.consumer.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Service
public class KafKaProducerService {

    @Value(value = "${general.topic.name}")
    private String topicName; // General topic with string payload

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${user.topic.name}")
    private String userTopicName; //Topic with user object payload

    @Autowired
    private KafkaTemplate<String, User> userKafkaTemplate;

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future
                = this.kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message: " + message
                        + " with offset: " + result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message : " + message, ex);
            }
        });
    }

    public void createUser(User user) {
        try {
            userKafkaTemplate.send(userTopicName, user);

        } catch (Exception e) {
            log.info("Error occurred while publishing the invoice to message broker");
            throw e;
        }
    }
}
