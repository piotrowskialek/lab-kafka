package com.example.labkafka;

import java.time.LocalDateTime;

public record Message(
    String sender,
    LocalDateTime at,
    String text
) {
}
