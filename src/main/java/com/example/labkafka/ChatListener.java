package com.example.labkafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;


import java.util.Map;

import org.apache.kafka.common.TopicPartition;

import static com.example.labkafka.configuration.KafkaTopicConfig.CHAT_TOPIC_NAME;

@Service
public class ChatListener implements ConsumerSeekAware {
    private static final Logger logger = LoggerFactory.getLogger(ChatListener.class);

    private final ChatService chatService;

    @Autowired
    public ChatListener(ChatService chatService) {
        this.chatService = chatService;
    }

    @KafkaListener(topics = CHAT_TOPIC_NAME)
    public void listenMessages(
        @Payload Message message,
        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition
    ) {

        logger.info("Received Message: {}", message);
        MessageWithPartition messageWithPartition = new MessageWithPartition(
            partition,
            message.sender(),
            message.at(),
            message.text()
        );
        chatService.consumeMessage(messageWithPartition);
    }

    @Override
    public void onPartitionsAssigned(Map<TopicPartition, Long> assignments, ConsumerSeekCallback callback) {
        assignments.keySet().stream()
            .filter(partition -> CHAT_TOPIC_NAME.equals(partition.topic()))
            .forEach(partition -> callback.seekToBeginning(CHAT_TOPIC_NAME, partition.partition()));
    }
}
