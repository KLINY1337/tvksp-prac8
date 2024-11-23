package com.tvkspprac8.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producer/api")
@RequiredArgsConstructor
@Slf4j
public class ProducerController {

    private final JdbcTemplate jdbcTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic topic;
    private long count = 0;

    @Scheduled(cron = "0 * * * * *")
    public void produceMessageToKafka() {
        log.info("!!!!PRODUCING MESSAGE TO KAFKA!!!!");
        String message = "kafka-message-%d".formatted(count);
        kafkaTemplate.send(topic.name(), message);
        count++;
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update("INSERT INTO random_strings (value) VALUES (:val)", new MapSqlParameterSource("val", message));
    }
}
