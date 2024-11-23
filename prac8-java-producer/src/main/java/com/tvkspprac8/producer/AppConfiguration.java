package com.tvkspprac8.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableKafka
@Configuration
public class AppConfiguration {

    @Bean
    public NewTopic topic() {
        return new NewTopic("test-topic", 1, (short) 1);
    }
}
