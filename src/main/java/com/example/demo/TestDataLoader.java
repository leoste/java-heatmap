package com.example.demo;

import com.example.demo.entity.CallLog;
import com.example.demo.repository.CallLogRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Configuration
public class TestDataLoader {

    @Bean
    public CommandLineRunner loadTestData(CallLogRepository repository) {
        return args -> {
            Random random = new Random();
            String[] statuses = {"COMPLETED", "MISSED", "FAILED"};

            for (int d = 0; d < 3; d++) {
                for (int i = 0; i < 50; i++) {
                    LocalDateTime start = LocalDateTime.now().minusDays(d).withHour(random.nextInt(24)).withMinute(random.nextInt(60));
                    int duration = random.nextInt(300);
                    LocalDateTime end = start.plusSeconds(duration);

                    CallLog call = new CallLog();
                    call.setId(UUID.randomUUID().toString());
                    call.setUserId("user-" + random.nextInt(5));
                    call.setUsername("User" + random.nextInt(5));
                    call.setOnOffNumber("100000000" + random.nextInt(10));
                    call.setContactNumber("200000000" + random.nextInt(10));
                    call.setStatus(statuses[random.nextInt(statuses.length)]);
                    call.setIncoming(random.nextInt(0,2));
                    call.setDuration(duration);
                    call.setStartedAt(Timestamp.valueOf(start).toLocalDateTime());
                    call.setEndedAt(Timestamp.valueOf(end).toLocalDateTime());

                    repository.save(call);
                }
            }
        };
    }
}
