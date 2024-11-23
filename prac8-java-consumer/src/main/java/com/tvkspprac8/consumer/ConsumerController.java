package com.tvkspprac8.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer/api")
@RequiredArgsConstructor
@Slf4j
public class ConsumerController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/strings")
    public List<String> getAllConsumedStrings() {
        return jdbcTemplate.query(
                "SELECT value FROM random_strings",
                (rs, rowNum) -> rs.getString("value")
        );
    }

    @KafkaListener(topics = "test-topic")
    public void listen(String message) {
        log.info("!!!!CONSUMING MESSAGE FROM KAFKA!!!!. MESSAGE: {}", message);
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update("INSERT INTO random_strings (value) VALUES (:val)", new MapSqlParameterSource("val", message));
    }
}
