package com.sample.Non_Blocking.HTTP.Invocation.API.service;



import com.sample.Non_Blocking.HTTP.Invocation.API.dto.RequestDTO;
import com.sample.Non_Blocking.HTTP.Invocation.API.model.ApiMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RestFactoryAsync implements ApiFactory {

    private final WebClient.Builder webClientBuilder;

    @Override
    public Mono<String> executeTarget(ApiMethod apiMethod,
                                      RequestDTO requestDTO,
                                      int timeoutMillis) {
        System.out.println("Executing target for " + apiMethod + " on thread: " + Thread.currentThread().getName());

        WebClient.RequestBodySpec request = webClientBuilder.build()
                .method(HttpMethod.valueOf(apiMethod.name()))
                .uri(uriBuilder -> {
                    URI uri = URI.create(requestDTO.getUrl());
                    UriBuilder builder = uriBuilder
                            .scheme(uri.getScheme())
                            .host(uri.getHost())
                            .path(uri.getPath());

                    if (requestDTO.getParams() != null) {
                        for (var param : requestDTO.getParams()) {
                            param.forEach(builder::queryParam);
                        }
                    }
                    return builder.build();
                })

                .headers(headers -> {
                    if (requestDTO.getHeaderVariables() != null) {
                        headers.setAll(requestDTO.getHeaderVariables());
                    }
                });

        if (requestDTO.getRequestBody() != null) {
            request.contentType(MediaType.valueOf(requestDTO.getBodyType()));
            return request.bodyValue(requestDTO.getRequestBody())
                    .retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(timeoutMillis));
        } else {
            return request.retrieve()
                    .bodyToMono(String.class)
                    .timeout(Duration.ofMillis(timeoutMillis));
        }
    }
}
