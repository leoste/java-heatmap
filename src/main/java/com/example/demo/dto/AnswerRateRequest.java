package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerRateRequest {
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date must be in format YYYY-MM-DD")
    private String dateInput;

    @NotNull
    @Min(value = 3, message = "numberOfShades must be at least 3")
    @Max(value = 10, message = "numberOfShades must be at most 10")
    private Integer numberOfShades;

    @Min(value = 0, message = "startHour must be between 0 and 23")
    @Max(value = 23, message = "startHour must be between 0 and 23")
    private Integer startHour = 0;

    @Min(value = 0, message = "endHour must be between 0 and 23")
    @Max(value = 23, message = "endHour must be between 0 and 23")
    private Integer endHour = 23;

    @AssertTrue(message = "endHour must be greater than or equal to startHour")
    public boolean isEndHourValid() {
        return endHour >= startHour;
    }
}
