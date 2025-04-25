package com.example.demo.service;

import com.example.demo.dto.RateHour;
import com.example.demo.entity.CallLog;
import com.example.demo.repository.CallLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallLogService {

    private final CallLogRepository callLogRepository;

    @Autowired
    public CallLogService(CallLogRepository callLogRepository) {
        this.callLogRepository = callLogRepository;
    }

    public List<CallLog> getCallLogs() {
        return callLogRepository.findAll();
    }

    public List<RateHour> getFakeAnswerRate(String dateInput, Integer numberOfShades, Integer startHour, Integer endHour) {
        List<RateHour> rateHours = new ArrayList<>();
        RateHour rateHour = new RateHour(0, 15, 20);
        rateHours.add(rateHour);
        return rateHours;
    }
}
