package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/pgs")
@RequiredArgsConstructor
public class PGController {
    private final PGService pgService;

    @PostMapping
    public ResponseEntity<PGResponse> create(@RequestBody PGRequest dto) {
        return ResponseEntity.ok(pgService.createPG(dto));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PGResponse>> list(@PathVariable String userId) {
        return ResponseEntity.ok(pgService.getPGsByUser(userId));
    }
}
