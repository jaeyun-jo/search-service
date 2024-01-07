package com.searchservice.exceptions;

import com.searchservice.apiserver.common.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

import static com.searchservice.apiserver.common.CommonResponse.error;

@Slf4j
@ControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler({
            BindException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class
    })
    public ResponseEntity<CommonResponse<Void>> handleBadRequest(Exception e) {
        log.warn("BadRequest occurred. message={}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(error(e.getMessage()));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<CommonResponse> handleThrowable(Throwable e) {
        log.error("Throwable occurred. message={}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error("Internal Server Error"));
    }
}
