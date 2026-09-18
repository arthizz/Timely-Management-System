package com.art.timelymanagementsystem.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "time_in", nullable = false)
    private LocalDateTime timeIn;

    @Column(name = "time_out")
    private LocalDateTime timeOut;

    @Column(name = "is_night_shift", nullable = false)
    private Boolean isNightShift;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "timeLog", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TimeLogPause> timeLogPause;

    @PrePersist
    public void onCreate(){

        createdAt = LocalDateTime.now();

    }

}
