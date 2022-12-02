package com.example.labkafka;

import com.example.labkafka.configuration.LabGroupProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@RestController
@RequestMapping("chat")
public class ChatEndpoint {
    private final ChatWriter chatWriter;
    private final ChatService chatService;
    private final LabGroupProperties labGroupProperties;

    @Autowired
    public ChatEndpoint(ChatWriter chatWriter,
                        ChatService chatService,
                        LabGroupProperties labGroupProperties) {
        this.chatWriter = chatWriter;
        this.chatService = chatService;
        this.labGroupProperties = labGroupProperties;
    }

    @GetMapping("/send/{text}")
    public Message sent(@PathVariable("text") String text) {
        String sender = labGroupProperties.getName();
        Message message = new Message(sender, LocalDateTime.now(), text);
        chatWriter.writeMessage(message);
        return message;
    }

    @GetMapping(value = "/read", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageWithPartition> read(
            @RequestParam(value = "sender", required = false) String sender
    ) {
        return chatService.readMessages().filter(message -> true);
    }
}
