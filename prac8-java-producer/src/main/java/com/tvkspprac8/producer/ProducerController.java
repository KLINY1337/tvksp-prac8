package com.tvkspprac8.producer;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/producer/api")
@RequiredArgsConstructor
public class ProducerController {

    private final JdbcTemplate jdbcTemplate;

    @GetMapping("/strings")
    public List<String> getAllProducedStrings() {
        return jdbcTemplate.query(
                "SELECT value FROM random_strings",
                (rs, rowNum) -> rs.getString("value")
        );
    }
}
