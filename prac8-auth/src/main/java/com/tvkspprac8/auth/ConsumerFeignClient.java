package com.tvkspprac8.auth;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "consumerClient", url = "http://java-consumer-service:8081/consumer/api")
public interface ConsumerFeignClient {

    @GetMapping("/strings")
    ResponseEntity<List<String>> getAllConsumedStrings(
            @RequestHeader("X-User-Token") String userToken,
            @RequestBody List<String> availableTokens
    );
}
