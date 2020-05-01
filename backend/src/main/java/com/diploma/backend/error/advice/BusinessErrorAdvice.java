package com.diploma.backend.error.advice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.diploma.backend.error.exceptions.ResourceNotFoundException;
import com.diploma.backend.error.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BusinessErrorAdvice {

    @Value("${application.stackTraceEnabled}")
    private boolean isStackTraceEnabled;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public List<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return createErrorResponseFromMethodArgumentNotValidException(ex, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public List<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String stackTrace = isStackTraceEnabled ? ExceptionUtils.getStackTrace(ex) : null;
        return Collections.singletonList(new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value(), stackTrace));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public List<ErrorResponse> handleResourceNotFoundException(Exception ex) {
        String stackTrace = isStackTraceEnabled ? ExceptionUtils.getStackTrace(ex) : null;
        return Collections.singletonList(new ErrorResponse(ex.getCause().getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(), stackTrace));
    }

    private List<ErrorResponse> createErrorResponseFromMethodArgumentNotValidException(MethodArgumentNotValidException ex, int status) {
        String stackTrace = isStackTraceEnabled ? ExceptionUtils.getStackTrace(ex) : null;
        return ex.getBindingResult().getAllErrors().stream()
                .map(objectError -> new ErrorResponse(objectError.getDefaultMessage(), status, stackTrace))
                .collect(Collectors.toList());
    }

}
