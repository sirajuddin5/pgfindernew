package com.thryve.pgfinder.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class PGResponseHandler {
    public  static ResponseEntity <Object> ResponseBuilder(String Message, HttpStatus httpStatus, Object responseObject){
        Map<String, Object> response = new HashMap<>();
        response.put("Message", Message);
        response.put("HttpStatus", httpStatus);
        response.put("Data", responseObject);
        return new ResponseEntity<>(response, httpStatus);

    }
}