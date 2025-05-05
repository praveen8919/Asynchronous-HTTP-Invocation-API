package com.sample.Non_Blocking.HTTP.Invocation.API.service;


import com.sample.Non_Blocking.HTTP.Invocation.API.dto.RequestDTO;
import com.sample.Non_Blocking.HTTP.Invocation.API.model.ApiMethod;
import reactor.core.publisher.Mono;

public interface ApiFactory {
    Mono<String> executeTarget(ApiMethod apiMethod,
                               RequestDTO requestDTO,
                               int timeoutMillis);
}
