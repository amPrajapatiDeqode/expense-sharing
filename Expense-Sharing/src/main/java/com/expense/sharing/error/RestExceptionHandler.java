package com.expense.sharing.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ AmountMismatchException.class })
    public ResponseEntity<Object> handleAmountMismatchException(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( "Total amount does not matches with split amount...", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ InvalidSplitType.class })
    public ResponseEntity<Object> handleInvalidSplitType(
            Exception ex, WebRequest request) {
        return new ResponseEntity<Object>( "Please define a valid split type...", new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

}
