package com.example.demo.controller;

import com.example.demo.dto.AnswerRateRequest;
import com.example.demo.dto.RateHour;
import com.example.demo.entity.CallLog;
import com.example.demo.service.CallLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/heatmap")
public class CallLogController {

    private final CallLogService callLogService;

    @Autowired
    public CallLogController(CallLogService callLogService) {
        this.callLogService = callLogService;
    }

    @GetMapping("/answer-rate")
    public List<RateHour> getCallLogAnswerRate(@Valid @ModelAttribute AnswerRateRequest request) {
        return callLogService.getFakeAnswerRate(
                request.getDateInput(),
                request.getNumberOfShades(),
                request.getStartHour(),
                request.getEndHour()
        );
    }

    @GetMapping("/call-logs")
    public List<CallLog> getAllCallLog() {
        return callLogService.getCallLogs();
    }
}
