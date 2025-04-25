package com.example.demo.service;

import com.example.demo.dto.RateHour;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CallLogService {

    public List<RateHour> getFakeAnswerRate(String dateInput, Integer numberOfShades, Integer startHour, Integer endHour) {
        List<RateHour> rateHours = new ArrayList<>();
        RateHour rateHour = new RateHour(0, 15, 20);
        rateHours.add(rateHour);
        return rateHours;
    }
}
