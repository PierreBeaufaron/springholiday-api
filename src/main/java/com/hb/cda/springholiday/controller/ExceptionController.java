package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.business.exception.BookingException;
import com.hb.cda.springholiday.business.exception.BusinessException;
import com.hb.cda.springholiday.business.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ProblemDetail userExists(BusinessException e) {
        if(e instanceof UserAlreadyExistsException) {
            return ProblemDetail
                    .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ProblemDetail
                .forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Unknown server error.");
    }

    @ExceptionHandler(BookingException.class)
    public ProblemDetail bookingException(BookingException e) {
        return  ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ProblemDetail bookingError(NoSuchElementException e){
        return ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    //  @ExceptionHandler(ExampleException.class)
    // public ProblemDetail example(ExampleException e){
    //         return ProblemDetail
    //         .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    // }

}
