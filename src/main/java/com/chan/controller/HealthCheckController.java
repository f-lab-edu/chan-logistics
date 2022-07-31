package com.chan.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
public class HealthCheckController {

    @GetMapping("/")
    public Object Hi(){
        HashMap<String, String> result = new HashMap<>();
        result.put("data", "health check");
        return result;
    }
}
