package com.nam4o.myweb.common;

import com.nam4o.myweb.common.exception.ErrorCode;
import com.nam4o.myweb.common.exception.Exceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<? extends BaseResponseBody> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors()
                .forEach(c -> errors.put(((FieldError) c).getField(), c.getDefaultMessage()));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BaseResponseBody.error(ErrorCode.NOT_VALID_REQUEST.getErrorCode(), errors));
    }
}
