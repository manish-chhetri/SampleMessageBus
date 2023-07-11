package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SubscribeRequest {
    @JsonProperty("topic_name")
    String topicName;

    @JsonProperty("consumer_name")
    String consumerName;
}
