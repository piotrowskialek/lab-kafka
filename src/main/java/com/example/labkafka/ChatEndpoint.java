package com.example.labkafka;

import com.example.labkafka.configuration.LabGroupProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    public ChatEndpoint(ChatWriter chatWriter, ChatService chatService, LabGroupProperties labGroupProperties) {
        this.chatWriter = chatWriter;
        this.chatService = chatService;
        this.labGroupProperties = labGroupProperties;
    }

    @PostMapping("/send/")
    public Message send(@RequestBody String text) {
        String sender = labGroupProperties.name();
        Message message = new Message(sender, LocalDateTime.now(), text);
        chatWriter.writeMessage(message);
        return message;
    }

    @GetMapping(value = "/read", produces = TEXT_EVENT_STREAM_VALUE)
    public Flux<MessageWithPartition> read(
        @RequestParam(value = "sender", required = false) String sender
    ) {
        if (sender == null) {
            return chatService.readMessages();
        }
        return chatService.readMessages().filter(message -> sender.equals(message.sender()));
    }
}
