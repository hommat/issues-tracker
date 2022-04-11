package org.example.rest.v1;

import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Getter
public class RestErrorResponse {
    private String message;
    private Map<String, Set<String>> errors;

    public RestErrorResponse(String message) {
        this.message = message;
    }

    public RestErrorResponse(Map<String, Set<String>> errors) {
        this.errors = errors;
    }

    public RestErrorResponse(String message, Map<String, Set<String>> errors) {
        this.message = message;
        this.errors = errors;
    }

    public Map<String, Object> toAttributeMap() {
        return Map.of("message", message);
    }
}
