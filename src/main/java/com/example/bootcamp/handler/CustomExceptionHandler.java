package com.example.bootcamp.handler;

import com.example.bootcamp.exception.RemoteServiceException;
import com.example.bootcamp.exception.RoleMismatchException;
import io.netty.handler.timeout.ReadTimeoutException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RoleMismatchException.class)
    protected ResponseEntity<CustomException> handleRoleMismatchException() {
        return new ResponseEntity<>(new CustomException("Invalid candidate role"), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RemoteServiceException.class)
    protected ResponseEntity<CustomException> handleRemoteServiceException() {
        return new ResponseEntity<>(new CustomException("Remote server problem"), HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ReadTimeoutException.class)
    protected ResponseEntity<CustomException> handleReadTimeoutExceptionException() {
        return new ResponseEntity<>(new CustomException("Remote server timeout"), HttpStatus.GATEWAY_TIMEOUT);
    }

    @Data
    @AllArgsConstructor
    private static class CustomException {
        private String message;
    }
}
