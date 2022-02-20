package com.example.kafka.producer.consumer.controller;

import com.example.kafka.producer.consumer.model.User;
import com.example.kafka.producer.consumer.service.KafKaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
    private final KafKaProducerService producerService;


    @PostMapping(value = "/publish")
    public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
        this.producerService.sendMessage(message);
    }

    @PostMapping(value = "/createUser")
    public void sendMessageToKafkaTopic(@RequestBody User user) {
        log.info("Input Parameter User {}", user);
        producerService.createUser(user);
    }
}