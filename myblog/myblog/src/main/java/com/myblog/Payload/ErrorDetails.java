package com.myblog.Payload;

import  lombok.Data;

import java.util.Date;

@Data
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String description;

    public ErrorDetails(Date timestamp ,String message,String description) {
        this.message = message;
        this.timestamp = timestamp;
        this.description= description;
    }


}
