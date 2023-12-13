package com.example.labkafka.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties("lab.group")
public record LabGroupProperties(String name) {

}
