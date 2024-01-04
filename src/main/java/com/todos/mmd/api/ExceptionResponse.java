package com.todos.mmd.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor // Exception Handler에서 사용하기 위해 생성자 필요
public class ExceptionResponse {
    private final String exceptionMessage;
    private final LocalDateTime timeStamp;
}
