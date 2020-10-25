package com.zaki.imdb.imdb.web.advice;

import com.zaki.imdb.imdb.exception.ValidationErrorsException;
import com.zaki.imdb.imdb.exception.base.BaseIMDBException;
import com.zaki.imdb.imdb.model.ErrorResponse;
import com.zaki.imdb.imdb.util.ExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleBaseIMDBException(BaseIMDBException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ErrorResponse(400, ex.getMessage()));
    }

    @ExceptionHandler({TransactionSystemException.class, ValidationErrorsException.class})
    public ResponseEntity<ErrorResponse> handleValidationErrors(RuntimeException rtex) {
        ValidationErrorsException ex = ExceptionUtils.extractConstraintViolationException(rtex);

        ErrorResponse errorResponse = new ErrorResponse(400, "Post not valid");
        if (ex.getErrors() != null) {
            ex.getErrors().getAllErrors().forEach(err -> {
                if (err.contains(ConstraintViolation.class)) {
                    ConstraintViolation cv = err.unwrap(ConstraintViolation.class);
                    String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                    errorResponse.getConstraintViolations().add(vStr);
                } else if (err.contains(Exception.class)) {
                    errorResponse.getExceptionMessages().add(err.unwrap(Exception.class).getMessage());
                }
            });
        } else {
            ex.getViolations().forEach(cv -> {
                String vStr = String.format("%s[%s]: '%s' - %s", cv.getLeafBean().getClass().getSimpleName(), cv.getPropertyPath(), cv.getInvalidValue(), cv.getMessage());
                errorResponse.getConstraintViolations().add(vStr);
            });
        }
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
