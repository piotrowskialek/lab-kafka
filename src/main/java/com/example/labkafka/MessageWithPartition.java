package com.example.labkafka;

import java.time.LocalDateTime;

record MessageWithPartition(
    Integer partition,
    String sender,
    LocalDateTime at,
    String text
) {
}
