package com.example.demo.dto;

import lombok.Data;

@Data
public class RateHour {
    private Integer hour;
    private Integer answeredCalls;
    private Integer totalCalls;
    private Float rate;
    private Shade shade;

    public RateHour(Integer hour, Integer answeredCalls, Integer totalCalls) {
        this.hour = hour;
        this.answeredCalls = answeredCalls;
        this.totalCalls = totalCalls;
        this.rate = calculateRate();
        this.shade = calculateShade();
    }

    private Float calculateRate() {
        if (totalCalls == 0) {
            return 0.0f;
        }
        return (answeredCalls.floatValue() / totalCalls) * 100.0f;
    }

    private Shade calculateShade() {
        if (rate <= 20.0) {
            return Shade.Shade1;
        } else if (rate <= 40.0) {
            return Shade.Shade2;
        } else if (rate <= 60.0) {
            return Shade.Shade3;
        } else if (rate <= 80.0) {
            return Shade.Shade4;
        } else {
            return Shade.Shade5;
        }
    }
}
