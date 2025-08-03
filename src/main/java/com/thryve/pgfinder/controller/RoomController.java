package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.config.validation.UtilityValidation;
import com.thryve.pgfinder.dto.request.RoomRequest;
import com.thryve.pgfinder.model.common.filter.specification.FiltersSpecification;
import com.thryve.pgfinder.repository.RoomReposoitory;
import com.thryve.pgfinder.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class RoomController {
    @Autowired
    private final RoomService roomService;

    @PostMapping("/create-room")
    public ResponseEntity<?> createRoom(@RequestBody RoomRequest dto){
        return ResponseEntity.ok(this.roomService.createRoom(dto));
    }
}
