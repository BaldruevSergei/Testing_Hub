package org.example.testing_hub.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class EndpointsController {

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;

    @GetMapping("/api/endpoints")
    public Map<String, String> getEndpoints() {
        return handlerMapping.getHandlerMethods().entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> entry.getKey().toString(),
                        entry -> entry.getValue().getMethod().toString()
                ));
    }
}

