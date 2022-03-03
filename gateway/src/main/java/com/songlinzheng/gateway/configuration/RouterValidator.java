package com.songlinzheng.gateway.configuration;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Component
public class RouterValidator {
    public static final List<String> openApiEndpoints = Arrays.asList(new String[]{
            "/authentication/signup",
            "/authentication/login",
            "/authentication/logout",
            "/authentication/refresh"
    });
    public Predicate<ServerHttpRequest> isSecured = request -> openApiEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
