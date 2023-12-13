package com.example.labkafka.configuration;

import com.example.labkafka.Message;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
@EnableConfigurationProperties(LabGroupProperties.class)
public class KafkaConsumerConfig {
    private static final String CONSUMER_GROUP_PREFIX = "CONSUMER_GROUP_";

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    private final LabGroupProperties labGroupProperties;

    public KafkaConsumerConfig(LabGroupProperties labGroupProperties) {
        this.labGroupProperties = labGroupProperties;
    }

    @Bean
    public ConsumerFactory<String, Message> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(
            ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
            bootstrapAddress);
        props.put(
            ConsumerConfig.GROUP_ID_CONFIG,
            CONSUMER_GROUP_PREFIX + labGroupProperties.name());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        final JsonDeserializer<Message> messageJsonDeserializer = new JsonDeserializer<>();
        messageJsonDeserializer.trustedPackages("*");
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), messageJsonDeserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Message>
    kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, Message> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
