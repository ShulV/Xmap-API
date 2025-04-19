package com.xmap_api.controllers;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@ConditionalOnExpression("'${xmap-api.frontend.url}'.contains('localhost')")
public class TestController {

    @PostMapping("/test/post-request")
    public ResponseEntity<TestPostResponse> testPost(@RequestBody TestPostRequest request) {
        return ResponseEntity.ok(new TestPostResponse(request.data, LocalDateTime.now()));
    }


    public record TestPostResponse(
            String data,
            LocalDateTime someDate
    ) {}

    public record TestPostRequest(
            String data
    ) {}
}
