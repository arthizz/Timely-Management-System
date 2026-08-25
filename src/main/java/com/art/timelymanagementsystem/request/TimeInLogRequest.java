package com.art.timelymanagementsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeInLogRequest {

    private LocalDateTime timeIn;

}
