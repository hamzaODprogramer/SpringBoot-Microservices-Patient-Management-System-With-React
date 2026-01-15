package com.ps.billing_service.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/billing")
public class BillingController {
    @GetMapping
    public String Test() {
        return "Billing Service is up and running!";
    }
}
