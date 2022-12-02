package com.example.labkafka.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LabGroupProperties {
    @Value(value = "${lab.group.name}")
    private String name;

    public String getName() {
        return name;
    }
}
