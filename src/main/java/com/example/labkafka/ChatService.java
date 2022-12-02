package com.example.labkafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@Service
public class ChatService {
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final Sinks.Many<MessageWithPartition> messagesCache = Sinks.many().replay().all();

    public void consumeMessage(MessageWithPartition message) {
        try {
            messagesCache.tryEmitNext(message);
        } catch (Exception e) {
            logger.error("Got exception on consumption of message: {}",message, e );
        }
    }

    public Flux<MessageWithPartition> readMessages() {
        return messagesCache.asFlux();
    }

}
