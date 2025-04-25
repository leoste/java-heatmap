package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "CALL_LOG")
@Getter @Setter @NoArgsConstructor
public class CallLog {

    @Id
    @Column(name = "ID", length = 45)
    private String id;

    @Column(name = "USER_ID", length = 45)
    private String userId;

    @Column(name = "USERNAME", length = 255)
    private String username;

    @Column(name = "ONOFF_NUMBER", length = 45)
    private String onOffNumber;

    @Column(name = "CONTACT_NUMBER", length = 45)
    private String contactNumber;

    @Column(name = "STATUS", length = 45)
    private String status;

    @Column(name = "INCOMING")
    private int incoming;

    @Column(name = "DURATION")
    private int duration;

    @Column(name = "STARTED_AT")
    private LocalDateTime startedAt;

    @Column(name = "ENDED_AT")
    private LocalDateTime endedAt;
}
