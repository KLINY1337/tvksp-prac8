package com.tvkspprac8.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final ConsumerFeignClient consumerFeignClient;

    @PostMapping("/auth/register")
    public String registerUser() {
        String token = UUID.randomUUID().toString();
        userRepository.save(new User(token));
        return token;
    }

    @GetMapping("/consumer/strings")
    public ResponseEntity<List<String>> getConsumedStrings(@RequestHeader("X-User-Token") String token) {
        List<String> availableTokens = new LinkedList<>();
        userRepository.findAll()
                .forEach(user -> availableTokens.add(user.getId()));
        return consumerFeignClient.getAllConsumedStrings(
                token,
                availableTokens
        );
    }
}
