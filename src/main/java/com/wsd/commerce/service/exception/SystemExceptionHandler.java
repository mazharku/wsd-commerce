package com.wsd.commerce.service.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.wsd.commerce.model.exceptions.ErrorMessage;
import com.wsd.commerce.model.exceptions.ResourceNotFoundException;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class SystemExceptionHandler {
    @ExceptionHandler(SecurityException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ErrorMessage resourceNotFoundException(SecurityException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage authenticationException(BadCredentialsException ex, WebRequest request) {
        log.warn(ex.getMessage());
        return new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                LocalDate.now(),
                "wrong password",
                request.getDescription(false));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ErrorMessage resourceNotFoundException(HttpMessageNotReadableException ex, WebRequest request) {
        return new ErrorMessage(
                HttpStatus.BAD_REQUEST.value(),
                LocalDate.now(),
                ex.getMessage(),
                request.getDescription(false));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ErrorMessage accessDeniedException(AccessDeniedException ex, WebRequest request) {
        log.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorMessage(
                HttpStatus.FORBIDDEN.value(),
                LocalDate.now(),
                "user has no permission",
                request.getDescription(false));
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage globalExceptionHandler(Exception ex, WebRequest request) {
        log.error(Arrays.toString(ex.getStackTrace()));
        return new ErrorMessage(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDate.now(),
                "Something went wrong!",
                request.getDescription(false));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorMessage handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult result = e.getBindingResult();
        List<String> fieldValidationErrors = new ArrayList<>();

        result.getFieldErrors().forEach(fieldError -> fieldValidationErrors.add(fieldError.getField() + " => " + fieldError.getDefaultMessage()));
        return ErrorMessage.builder()
                .date(LocalDate.now())
                .message(String.join("/n", fieldValidationErrors))
                .description(null)
                .value(HttpStatus.BAD_REQUEST.value())
                .build();
    }
}
