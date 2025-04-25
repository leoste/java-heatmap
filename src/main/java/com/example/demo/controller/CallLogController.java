package com.example.demo.controller;

import com.example.demo.dto.RateHour;
import com.example.demo.service.CallLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        validateCallLogAnswerRateParameters(dateInput, numberOfShades, startHour, endHour);

        return callLogService.getFakeAnswerRate(dateInput, numberOfShades, startHour, endHour);
    }

    private static void validateCallLogAnswerRateParameters(String dateInput, Integer numberOfShades, Integer startHour, Integer endHour) {
        if (!dateInput.matches("\\d{4}-\\d{2}-\\d{2}")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid date format. Expected YYYY-MM-DD.");
        }

        if (numberOfShades < 3 || numberOfShades > 10) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "numberOfShades must be between 3 and 10.");
        }

        if (startHour < 0 || startHour > 23) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "startHour must be between 0 and 23.");
        }

        if (endHour < 0 || endHour > 23) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endHour must be between 0 and 23.");
        }

        if (endHour < startHour) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "endHour must be greater than or equal to startHour.");
        }
    }
}
