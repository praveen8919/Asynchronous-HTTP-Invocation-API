package com.sample.Non_Blocking.HTTP.Invocation.API.controller;


import com.sample.Non_Blocking.HTTP.Invocation.API.dto.RequestDTO;
import com.sample.Non_Blocking.HTTP.Invocation.API.model.ApiMethod;
import com.sample.Non_Blocking.HTTP.Invocation.API.service.ApiFactory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/invoke")
@RequiredArgsConstructor
public class HttpInvocationController {

    private final ApiFactory apiFactory;

    @PostMapping
    public Mono<ResponseEntity<String>> invoke(@RequestBody InvokeRequest request) {
        System.out.println("Received request on thread: " + Thread.currentThread().getName());

        return apiFactory.executeTarget(
                        request.getApiMethod(),
                      request.getRequestDTO(),
                        request.getTimeout())
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.internalServerError()
                        .body("Error: " + e.getMessage())));
    }

    @Data
    public static class InvokeRequest {
        private ApiMethod apiMethod;
        private RequestDTO requestDTO;
        private int timeout;
    }
}

