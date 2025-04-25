package com.example.demo.service;

import com.example.demo.dto.RateHour;
import com.example.demo.entity.CallLog;
import com.example.demo.entity.CallLogStatus;
import com.example.demo.repository.CallLogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class CallLogServiceTest {

    @InjectMocks
    private CallLogService callLogService;  // Service to test

    @Mock
    private CallLogRepository callLogRepository;  // Mocking the repository

    @Test
    void testGetAnswerRateWithNoCalls() {
        // Given
        String dateInput = "2024-04-25";
        int numberOfShades = 5;
        int startHour = 10;
        int endHour = 12;

        // Mock empty list of logs (no calls)
        when(callLogRepository.findByStartedAtBetween(Mockito.any(), Mockito.any()))
                .thenReturn(List.of());

        // When
        List<RateHour> result = callLogService.getAnswerRate(dateInput, numberOfShades, startHour, endHour);

        // Then
        assertEquals(3, result.size(), "Should return 3 rate hours with no calls");
        assertEquals(0, result.get(0).getTotalCalls(), "Total calls in hour 10 should be 0");
        assertEquals(0, result.get(0).getAnsweredCalls(), "Answered calls in hour 10 should be 0");
    }

    @Test
    void testGetAnswerRateWithDifferentStatuses() {
        // Given
        String dateInput = "2024-04-25";
        int numberOfShades = 5;
        int startHour = 10;
        int endHour = 12;

        List<CallLog> mockLogs = createMockLogsWithDifferentStatuses();

        when(callLogRepository.findByStartedAtBetween(Mockito.any(), Mockito.any()))
                .thenReturn(mockLogs);

        // When
        List<RateHour> result = callLogService.getAnswerRate(dateInput, numberOfShades, startHour, endHour);

        // Then
        assertEquals(3, result.size(), "Should return 3 rate hours");
        assertEquals(1, result.get(0).getAnsweredCalls(), "Answered calls in hour 10 should be 1");
        assertEquals(2, result.get(1).getAnsweredCalls(), "Answered calls in hour 11 should be 2");
        assertEquals(0, result.get(2).getAnsweredCalls(), "Answered calls in hour 12 should be 0");
    }

    @Test
    void testMaximumShadeWhenAllCallsAnswered() {
        // Given
        String dateInput = "2024-04-25";
        int numberOfShades = 10;
        int startHour = 10;
        int endHour = 12;

        List<CallLog> mockLogs = createMocklogs(CallLogStatus.ANSWER);

        when(callLogRepository.findByStartedAtBetween(Mockito.any(), Mockito.any()))
                .thenReturn(mockLogs);

        // When
        List<RateHour> result = callLogService.getAnswerRate(dateInput, numberOfShades, startHour, endHour);

        // Then
        assertEquals(3, result.size(), "Should return 3 rate hours");
        assertEquals("Shade10", result.get(0).getShade(), "Shade should be set to 10");
    }

    @Test
    void testMinimumShadeWhenNoCallsAnswered() {
        // Given
        String dateInput = "2024-04-25";
        int numberOfShades = 10;
        int startHour = 10;
        int endHour = 12;

        List<CallLog> mockLogs = createMocklogs(CallLogStatus.MISSED);

        when(callLogRepository.findByStartedAtBetween(Mockito.any(), Mockito.any()))
                .thenReturn(mockLogs);

        // When
        List<RateHour> result = callLogService.getAnswerRate(dateInput, numberOfShades, startHour, endHour);

        // Then
        assertEquals(3, result.size(), "Should return 3 rate hours");
        assertEquals("Shade1", result.get(0).getShade(), "Shade should be set to 1");
    }

    private List<CallLog> createMocklogs(CallLogStatus status) {
        List<CallLog> mockLogs = new ArrayList<>();

        mockLogs.add(new CallLog(
                "10",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                status.toString(),
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 10, 0, 0),
                LocalDateTime.of(2024, 4, 25, 10, 5, 0)
        ));

        return mockLogs;
    }

    private List<CallLog> createMockLogsWithDifferentStatuses() {
        List<CallLog> mockLogs = new ArrayList<>();

        mockLogs.add(new CallLog(
                "10",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "ANSWER",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 10, 0, 0),
                LocalDateTime.of(2024, 4, 25, 10, 5, 0)
        ));
        mockLogs.add(new CallLog(
                "11",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "MISSED",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 10, 10, 0),
                LocalDateTime.of(2024, 4, 25, 10, 15, 0)
        ));

        mockLogs.add(new CallLog(
                "12",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "ANSWER",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 11, 0, 0),
                LocalDateTime.of(2024, 4, 25, 11, 5, 0)
        ));
        mockLogs.add(new CallLog(
                "13",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "ANSWER",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 11, 10, 0),
                LocalDateTime.of(2024, 4, 25, 11, 15, 0)
        ));

        mockLogs.add(new CallLog(
                "14",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "MISSED",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 12, 0, 0),
                LocalDateTime.of(2024, 4, 25, 12, 5, 0)
        ));
        mockLogs.add(new CallLog(
                "15",
                "user1",
                "username1",
                "1234567890",
                "0987654321",
                "ERROR",
                1,
                300,
                LocalDateTime.of(2024, 4, 25, 12, 10, 0),
                LocalDateTime.of(2024, 4, 25, 12, 15, 0)
        ));

        return mockLogs;
    }
}
