package com.tidal.interview.tidal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PlaylistException.class)
    public ResponseEntity<CustomErrorResponse> handleCustomException(PlaylistException e) {
        CustomErrorResponse errorResponse = new CustomErrorResponse();
        errorResponse.setErrorCode(e.getCustomErrors());
        errorResponse.setErrorMessage(e.getCustomErrors().getErrorMessage());
        //Just giving BAD request for now
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
