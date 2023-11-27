package com.myblog.Exception;

import com.myblog.Payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)

    public ResponseEntity<ErrorDetails>getGlobelExceptionhandler(Exception exception,
                                                                 WebRequest webRequest){

        ErrorDetails errorDetails = new ErrorDetails(new Date() ,exception.getMessage()
                , webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails , HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
