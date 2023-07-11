package com.example.service;

import com.example.exception.ConsumerNotSubscribed;
import com.example.exception.MessageNotPushed;
import com.example.exception.TopicNotFound;
import com.example.model.Topic;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MessageBusService {

    Map<String, Topic> topics = new HashMap<String, Topic>();

    public Topic createTopic(@NonNull String name) {

        Topic topic = new Topic(UUID.randomUUID().toString(), name);
        topics.put(name, topic);

        System.out.println("Created topic: " + topic.getName());

        return topic;
    }

    public String push(@NonNull String topic_name,@NonNull String message) throws TopicNotFound, MessageNotPushed {

        if (!topics.keySet().contains(topic_name)) {
            throw new TopicNotFound("Topic not found");
        }

        Topic topic = topics.get(topic_name);
        topic.pushMessage(message);
        return "success";
    }

    public String poll(@NonNull String topic_name, @NonNull String consumerName) throws TopicNotFound, ConsumerNotSubscribed {

        if (!topics.keySet().contains(topic_name)) {
            throw new TopicNotFound("Topic not found");
        }

        Topic topic = topics.get(topic_name);
        String message = topic.pollMessage(consumerName);

        return message;

    }

    public String subscribe(@NonNull String topic_name, @NonNull String consumerName) throws TopicNotFound {
        String response = "Not Subscribed";

        if (!topics.keySet().contains(topic_name)) {
            throw new TopicNotFound("Topic not found");
        }

        Topic topic = topics.get(topic_name);
        response = topic.subscribe(consumerName);

        return response;
    }
}
