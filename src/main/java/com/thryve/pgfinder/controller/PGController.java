package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.dto.response.PGResponseHandler;
import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/pgs")
@RequiredArgsConstructor
public class PGController {
    private final PGService pgService;

    @PostMapping
    public  ResponseEntity<Object> create(@RequestBody PGRequest dto){

        return PGResponseHandler.ResponseBuilder("PG Added", HttpStatus.CREATED, pgService.createPG(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> list(@PathVariable String userId) {
        return PGResponseHandler.ResponseBuilder("Requested PG Details", HttpStatus.OK, pgService.getPGsByUser(userId));
    }
}
