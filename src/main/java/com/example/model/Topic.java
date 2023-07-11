package com.example.model;

import com.example.exception.ConsumerNotSubscribed;
import com.example.exception.MessageNotPushed;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Topic {
    private String id;
    private String name;
    private List<Message> messages;
    private List<Consumer> consumerList;
    private Map<Consumer, Integer> consumerMessageOffsetMap;

    public Topic(@NonNull String id, @NonNull String name) {
        this.id = id;
        this.name = name;
        this.messages = new ArrayList<Message>();
        this.consumerList = new ArrayList<Consumer>();
        this.consumerMessageOffsetMap = new HashMap<Consumer, Integer>();
    }

    public void pushMessage(String message) throws MessageNotPushed {

        Message messageObj = new Message(message);
        Boolean isAdded = this.messages.add(messageObj);
        int offset = this.messages.indexOf(messageObj);

        if (!isAdded) {
            throw new MessageNotPushed("Message not pushed");
        } else {
            // reset offset for new consumers
            for (Consumer consumerObj: this.consumerList) {
                Integer consumerOffset = this.consumerMessageOffsetMap.get(consumerObj);
                if (consumerOffset == null) {
                    this.consumerMessageOffsetMap.put(consumerObj, offset);
                }
            }
        }
    }

    public String pollMessage(String consumerName) throws ConsumerNotSubscribed {

        // Fetching consumer offset
        Consumer consumer = new Consumer(consumerName);

        //Verifying if consumer subscribed
        if (!consumerList.contains(consumer)) {
            throw new ConsumerNotSubscribed("Topic not subscribed");
        }

        Integer offset = this.consumerMessageOffsetMap.get(consumer);
        Message message = null;

        if (offset != null && offset >= 0) {
            int newOffset = offset;
            if( offset < this.messages.size()) {
                message = this.messages.get(offset);
            }

            if (message != null) {
                // update the offset for consumer
                newOffset++;
                this.consumerMessageOffsetMap.put(consumer, newOffset);
            }

            // remove Message read by everyone
            int minOffset = Collections.min(this.consumerMessageOffsetMap.values());

            if(minOffset < this.messages.size()) {
                for (int i = (int)minOffset-1; i >= 0 ; i--) {
                    this.messages.remove(i);
                    for (Map.Entry<Consumer, Integer> mapData: this.consumerMessageOffsetMap.entrySet()) {
                        this.consumerMessageOffsetMap.put(mapData.getKey(), mapData.getValue()-1);
                    }
                }
            }
        }

        return message != null ? message.getMessage() : "No message for this consumer";
    }

    public String subscribe(@NonNull String consumerName) {
        String response = "Already subscribed";
        Consumer consumer = new Consumer(consumerName);

        if (!this.consumerList.contains(consumer)) {
            this.consumerList.add(consumer);
            response = "Subscribed";
        }

        // Adding consumer offset
        this.consumerMessageOffsetMap.put(consumer, null);

        return response;
    }
}
