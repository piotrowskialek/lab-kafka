package com.example.labkafka;

import java.time.LocalDateTime;

public record MessageWithPartition(
    Integer partition,
    String sender,
    LocalDateTime at,
    String text
) {
}
