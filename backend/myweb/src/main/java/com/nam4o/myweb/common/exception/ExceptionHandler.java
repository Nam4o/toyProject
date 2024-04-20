package com.nam4o.myweb.common.exception;

import ch.qos.logback.core.model.processor.ModelHandlerException;
import com.nam4o.myweb.common.BaseResponseBody;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionHandler {
    // exception 예외처리 응답
    @org.springframework.web.bind.annotation.ExceptionHandler(Exceptions.class)
    public ResponseEntity<? extends BaseResponseBody> exceptions(Exceptions e) {
        BaseResponseBody<Object> errorResponse = BaseResponseBody.error(e.getErrorCode().getErrorCode(), e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(errorResponse);
    }

    // valid 유효성 검사에 대한 예외처리 응답
    @org.springframework.web.bind.annotation.ExceptionHandler(ModelHandlerException.class)
    public Object handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info("handleMethodArgumentNotValidException");
        log.info(e.toString());
        log.info(e.getMessage());
        BaseResponseBody<Object> errorResponse = BaseResponseBody.error(ErrorCode.NOT_VALID_REQUEST.getErrorCode(), ErrorCode.NOT_VALID_REQUEST.getMessage());

        return ResponseEntity.status(ErrorCode.NOT_VALID_REQUEST.getHttpStatus()).body(errorResponse);
    }

    // validated 유효성 검사에 대한 예외처리 응답
    @org.springframework.web.bind.annotation.ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e){
        log.info("handleConstraintViolationException");
        log.info(e.toString());
        log.info(e.getMessage());
        BaseResponseBody<Object> errorResponse = BaseResponseBody.error(ErrorCode.NOT_VALID_REQUEST.getErrorCode(), ErrorCode.NOT_VALID_REQUEST.getMessage());

        return ResponseEntity.status(ErrorCode.NOT_VALID_REQUEST.getHttpStatus()).body(errorResponse);
    }
}
