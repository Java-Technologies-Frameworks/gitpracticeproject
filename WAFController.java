package com.example.waf.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.waf.model.WAFRule;
import com.example.waf.service.WAFService;

import java.util.List;

@RestController
public class WAFController {

    private final WAFService wafService;

    // Constructor injection for the WAFService that manages WAF rules
    public WAFControlle
