package com.example.labkafka.infra;

import com.example.labkafka.Message;
import com.example.labkafka.configuration.LabGroupProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import static com.example.labkafka.configuration.KafkaTopicConfig.CHAT_TOPIC_NAME;

@Service
public class ChatWriter {
    private static final Logger logger = LoggerFactory.getLogger(ChatWriter.class);

    private final LabGroupProperties labGroupProperties;
    private final KafkaTemplate<String, Message> kafkaTemplate;

    public ChatWriter(LabGroupProperties labGroupProperties, KafkaTemplate<String, Message> kafkaTemplate) {
        this.labGroupProperties = labGroupProperties;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void writeMessage(Message message) {

        String partitionKey = labGroupProperties.name();
        ListenableFuture<SendResult<String, Message>> future =
            kafkaTemplate.send(CHAT_TOPIC_NAME, partitionKey, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Message> result) {
                logger.info("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Got exception on sending message [{}],: ", message, ex);
            }
        });
    }
}
