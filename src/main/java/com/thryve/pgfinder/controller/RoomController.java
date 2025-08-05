package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.RoomRequest;
import com.thryve.pgfinder.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/update-room/{roomId}")
    public ResponseEntity<?> upadteRoom(@PathVariable String roomId, @RequestBody RoomRequest dto){
        return ResponseEntity.ok(roomService.updateRoom(roomId, dto));
    }
}
