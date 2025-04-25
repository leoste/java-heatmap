package com.example.demo.dto;

import lombok.Data;

@Data
public class RateHour {
    private int hour;
    private int answeredCalls;
    private int totalCalls;
    private float rate;
    private String shade;

    public RateHour(int hour, int answeredCalls, int totalCalls, int numberOfShades) {
        this.hour = hour;
        this.answeredCalls = answeredCalls;
        this.totalCalls = totalCalls;
        this.rate = calculateRate();
        this.shade = calculateShade(numberOfShades);
    }

    private Float calculateRate() {
        if (totalCalls == 0) {
            return 0.0f;
        }
        return (answeredCalls / (float)totalCalls) * 100.0f;
    }

    private String calculateShade(int numberOfShades) {
        float range = 100.0f / numberOfShades;
        int shadeLevel = (int) Math.ceil(rate / range);
        shadeLevel = Math.min(Math.max(shadeLevel, 1), numberOfShades); // ensure within bounds
        return "Shade" + shadeLevel;
    }
}
