package com.example.demo.service;

import com.example.demo.dto.RateHour;
import com.example.demo.entity.CallLog;
import com.example.demo.entity.CallLogStatus;
import com.example.demo.repository.CallLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

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

    public List<RateHour> getAnswerRate(String dateInput, int numberOfShades, int startHour, int endHour) {
        LocalDate targetDate = LocalDate.parse(dateInput);
        LocalDateTime startDateTime = targetDate.atTime(startHour, 0);
        LocalDateTime endDateTime = targetDate.atTime(endHour, 59, 59);

        List<CallLog> logs = callLogRepository.findByStartedAtBetween(startDateTime, endDateTime);

        return IntStream.rangeClosed(startHour, endHour)
                .mapToObj(hour -> toRateHour(hour, logsForHour(logs, hour), numberOfShades))
                .toList();
    }

    private List<CallLog> logsForHour(List<CallLog> logs, int hour) {
        return logs.stream()
                .filter(log -> log.getStartedAt().getHour() == hour)
                .toList();
    }

    private RateHour toRateHour(int hour, List<CallLog> logs, int shades) {
        int total = logs.size();
        int answered = (int) logs.stream()
                .filter(log -> CallLogStatus.ANSWER.toString().equalsIgnoreCase(log.getStatus()))
                .count();

        return new RateHour(hour, answered, total, shades);
    }
}
