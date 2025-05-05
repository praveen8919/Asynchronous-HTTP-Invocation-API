package com.sample.Non_Blocking.HTTP.Invocation.API.dto;

import com.sample.Non_Blocking.HTTP.Invocation.API.model.ApiMethod;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class RequestDTO {
    private String url;
    private Map<String, String> headerVariables;
    private List<Map<String, String>> params;
    private String bodyType;
    private String requestBody;
}

