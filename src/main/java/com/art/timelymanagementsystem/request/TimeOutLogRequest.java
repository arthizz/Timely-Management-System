package com.art.timelymanagementsystem.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeOutLogRequest {

    private LocalDateTime timeOut;

}
