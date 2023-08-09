Library for message queues that support consumer groups, the library must provide

**Create** an in memory message queue -

**Topic** - a logical separation across messages

**Producer** must be able to add messages to a specific topic in the queue.

**Consumer** must be able to read from the queue for a topic.

**Feature -**

**Consumer groups -**

**Consumers** can create separate groups which means that a shared pool of consumers can read
from a topic and a message is not deleted until all the consumers have read the message

**CreateTopic**

- String Topic name

**CreateConsumerGroup**
- String Topic name

**Poll**
- String TopicName
- Returns Message

**Push**

- String TopicName
- Message msg

The expectation, actual -
- Msgs 1, 2, 3, 4, 5

topic - logs (1, 2, 3, 4, 5), (cgs: analytics, dev)
- cg : analytics (1, 2, 3, 4, 5)
- cg: dev (1, 2, 3, 4, 5)

APIs that have to be implemented -

createTopic(topicName string, consumeGroups []string)

poll(topicName string, consumerGroup string -> optional)

in case consumerGroup is available, it is mandatory to be made available in the poll API

push(topicName string)
