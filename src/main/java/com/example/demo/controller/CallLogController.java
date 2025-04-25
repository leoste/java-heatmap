package com.example.demo.controller;

import com.example.demo.dto.RateHour;
import com.example.demo.service.CallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/heatmap")
public class CallLogController {

    private final CallLogService callLogService;

    @Autowired
    public CallLogController(CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    @GetMapping("/answer-rate")
    public List<RateHour> getCallLogAnswerRate(
            @RequestParam String dateInput,
            @RequestParam Integer numberOfShades,
            @RequestParam(required = false, defaultValue = "0") Integer startHour,
            @RequestParam(required = false, defaultValue = "23") Integer endHour
    ) {
        return callLogService.getFakeAnswerRate(dateInput, numberOfShades, startHour, endHour);
    }
}
