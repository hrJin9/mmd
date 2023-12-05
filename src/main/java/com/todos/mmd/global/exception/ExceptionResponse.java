package com.todos.mmd.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor // Exception Handler에서 사용하기 위해 생성자 필요
public class ExceptionResponse {
    private final String exceptionMessage;
    private final LocalDateTime timeStamp;
}
