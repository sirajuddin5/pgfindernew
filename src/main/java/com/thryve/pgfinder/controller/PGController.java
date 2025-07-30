package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.dto.response.PGResponseHandler;
import com.thryve.pgfinder.exception.PGAlreadyExistException;
import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/pgs")
@RequiredArgsConstructor
public class PGController {
    private final PGService pgService;

    @PostMapping("/create-pg")
    public  ResponseEntity<Object> create(@RequestBody PGRequest dto){
        return PGResponseHandler.ResponseBuilder("PG Added", HttpStatus.CREATED, pgService.createPG(dto));
    }

    @GetMapping("/user/all-pgs")
    public ResponseEntity<Object> ListAllpG(){
        return PGResponseHandler.ResponseBuilder("All PG details", HttpStatus.OK, pgService.getAllPgs());
    }

    @GetMapping("/user/{userId}/owned-pgs")
    public ResponseEntity<Object> list(@PathVariable String userId) {
        return PGResponseHandler.ResponseBuilder("Requested PG Details", HttpStatus.OK, pgService.getPGsByUser(userId));
    }

    @PostMapping("/update-pg/{pgId}")
    public ResponseEntity<Object> update(@RequestBody PGRequest dto){
        return  PGResponseHandler.ResponseBuilder("PG Updated", HttpStatus.OK, pgService.updatePG(dto));
    }

    @ExceptionHandler(PGAlreadyExistException.class)
        public ResponseEntity<Object> handleDuplicatePG(PGAlreadyExistException ex){
            Map<String, Object> body = new HashMap<>();
            body.put("status", HttpStatus.CONFLICT.value());
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }

}
