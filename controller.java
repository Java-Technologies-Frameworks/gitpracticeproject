package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController // This annotation marks the class as a controller for REST APIs
@RequestMapping("/api") // Base URL mapping for all methods in this controller
public class MyController {

    // This endpoint maps to "/api/hello" and returns a simple greeting message.
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, welcome to our API!";
    }

    // This endpoint maps to "/api/greet/{name}" and returns a greeting with a name passed as a path variable.
    @GetMapping("/greet/{name}")
    public String greet(@PathVariable String name) {
        return "Hello, " + name + "!";
    }

    // This endpoint maps to "/api/add/{a}/{b}" and returns the sum of the two numbers.
    @GetMapping("/add/{a}/{b}")
    public String add(@PathVariable int a, @PathVariable int b) {
        int sum = a + b;
        return "The sum of " + a + " and " + b + " is " + sum;
    }
}
