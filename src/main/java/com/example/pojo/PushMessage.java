package com.example.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PushMessage {
    @JsonProperty("topic_name")
    String topicName;
    String message;
}
