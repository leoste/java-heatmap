package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/heatmap")
public class CallLogController {

    @GetMapping("/test")
    public String test() {
        return "works";
    }

}
