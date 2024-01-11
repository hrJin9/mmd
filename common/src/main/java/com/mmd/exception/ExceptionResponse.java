package com.mmd.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ExceptionResponse {
    private final String exceptionMessage;
    private final LocalDateTime timeStamp;
}
