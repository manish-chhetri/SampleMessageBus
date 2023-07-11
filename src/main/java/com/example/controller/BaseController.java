package com.example.controller;

import com.example.exception.ConsumerNotSubscribed;
import com.example.pojo.CreateTopic;
import com.example.pojo.PushMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.pojo.PollMessage;
import com.example.pojo.SubscribeRequest;
import com.example.service.MessageBusService;

@RestController
@RequestMapping("/api/message_bus")
public class BaseController {

    @Autowired
    MessageBusService messageBusService;

    @PostMapping("/create_topic")
    public ResponseEntity<?> createTopic(@RequestBody CreateTopic createTopic) {
        try {
            messageBusService.createTopic(createTopic.getTopicName());
            return new ResponseEntity<>("ok", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/push")
    public ResponseEntity<?> push(@RequestBody PushMessage pushMessage) {
        try {
            String response = messageBusService.push(pushMessage.getTopicName(), pushMessage.getMessage());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/poll")
    public ResponseEntity<?> poll(@RequestBody PollMessage pollMessage) {
        try {
            String response = messageBusService.poll(pollMessage.getTopicName(), pollMessage.getConsumerName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception | ConsumerNotSubscribed e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestBody SubscribeRequest subscribeRequest) {
        try {
            String response = messageBusService.subscribe(subscribeRequest.getTopicName(), subscribeRequest.getConsumerName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
