package com.fadata.course.restmvc.web;

import com.fadata.course.restmvc.exception.InvalidEntityDataException;
import com.fadata.course.restmvc.exception.NonExistingEntityException;
import com.fadata.course.restmvc.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityData(InvalidEntityDataException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(400, ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleInvalidEntityData(NonExistingEntityException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(404, ex.getMessage()));
    }
}
