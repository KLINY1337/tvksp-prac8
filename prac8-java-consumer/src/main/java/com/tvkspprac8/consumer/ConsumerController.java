package com.tvkspprac8.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
    public ResponseEntity<List<String>> getAllConsumedStrings(
            @RequestHeader("X-User-Token") String userToken,
            @RequestBody List<String> availableTokens
    ) {
        if (availableTokens.contains(userToken)) {
            return ResponseEntity.ok(jdbcTemplate.query(
                    "SELECT value FROM random_strings",
                    (rs, rowNum) -> rs.getString("value")
            ));
        } else {
            return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, "You are using wrong token")).build();
        }
    }

    @KafkaListener(id = "consumerAppId", topics = "test-topic")
    public void listen(String message) {
        log.info("!!!!CONSUMING MESSAGE FROM KAFKA!!!!. MESSAGE: {}", message);
        NamedParameterJdbcTemplate namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
        namedJdbcTemplate.update("INSERT INTO random_strings (value) VALUES (:val)", new MapSqlParameterSource("val", message));
    }
}
