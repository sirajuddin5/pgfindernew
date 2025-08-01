package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.dto.response.PGResponseHandler;

import com.thryve.pgfinder.exception.AlreadyExistException;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class PGController {
	@Autowired
    private final PGService pgService;

    @PostMapping("/create-pg")
    public  ResponseEntity<?>  create(@RequestBody PGRequest dto){
        return ResponseEntity.ok(this.pgService.createPG(dto));
    }

    @PostMapping("/all-pgs")
    public ResponseEntity<?> ListAllpG(@RequestBody FetchAPIRequest fetchAPIRequest){
        return ResponseEntity.ok(this.pgService.fetchAll(fetchAPIRequest));
    }

    @GetMapping("/owned-pgs/{userId}")
    public ResponseEntity<Object> list(@PathVariable String userId) {
        return PGResponseHandler.ResponseBuilder("Requested PG Details", HttpStatus.OK, pgService.getPGsByUser(userId));
    }

    @PutMapping("/update-pg/{pgId}")
    public ResponseEntity<?> update(@PathVariable String pgId, @RequestBody PGRequest dto) {
//        PGResponse response = pgService.updatePG(String pgId, PGRequest dto);
//        return PGResponseHandler.ResponseBuilder("PG Updated", HttpStatus.OK, response);
        return ResponseEntity.ok(this.pgService.updatePG(pgId, dto));
    }

    @ExceptionHandler(AlreadyExistException.class)
        public ResponseEntity<Object> handleDuplicatePG(AlreadyExistException ex){
            Map<String, Object> body = new HashMap<>();
            body.put("status", HttpStatus.CONFLICT.value());
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }

}
