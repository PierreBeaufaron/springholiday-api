package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.business.exception.BookingException;
import com.hb.cda.springholiday.business.exception.BusinessException;
import com.hb.cda.springholiday.business.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ProblemDetail bookingException(BusinessException e) {
        return  ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}
