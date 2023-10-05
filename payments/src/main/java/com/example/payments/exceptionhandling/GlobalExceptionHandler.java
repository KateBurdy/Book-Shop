package com.example.payments.exceptionhandling;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String REQUEST_RAISED_EXCEPTION = "Request: %s raised %s";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(HttpServletRequest req, MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        log.error("Validation error: {}", errorMessage);
        return genericHandler(req, ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception e, HttpServletRequest req) {
        log.error("Generic error: ", e);
        return genericHandler(req, e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> genericHandler(HttpServletRequest req, Exception ex, HttpStatus defaultHttpStatus) {
        logException(req, ex);
        return buildResponse(ex, defaultHttpStatus);
    }

    private void logException(HttpServletRequest req, Exception ex) {
        log.error(String.format(REQUEST_RAISED_EXCEPTION, req.getRequestURL(), ex), ex);
    }

    private ResponseEntity<Object> buildResponse(Exception ex, HttpStatus defaultHttpStatus) {
        PaymentsApiException response = buildErrorResponse(ex, defaultHttpStatus);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    private PaymentsApiException buildErrorResponse(Exception ex, HttpStatus defaultHttpStatus) {
        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);

        int status = defaultHttpStatus.value();
        String error = defaultHttpStatus.getReasonPhrase();
        String message = ex.getMessage();

        if (responseStatusAnnotation != null) {
            status = responseStatusAnnotation.value().value();
            error = responseStatusAnnotation.value().getReasonPhrase();
        }

        if (responseStatusAnnotation != null && !responseStatusAnnotation.reason().isEmpty()) {
            message = responseStatusAnnotation.reason();
        }

        return PaymentsApiException.builder()
                .status(status)
                .error(error)
                .message(message)
                .timestamp(System.currentTimeMillis() + "")
                .build();
    }
}

