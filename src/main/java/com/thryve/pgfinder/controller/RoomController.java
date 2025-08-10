package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.RoomRequest;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;
import com.thryve.pgfinder.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class RoomController {
//    @Autowired
//    private final RoomService roomService;
//
//    @PostMapping("/create-room")
//    public ResponseEntity<?> createRoom(@RequestBody RoomRequest dto){
//        return ResponseEntity.ok(this.roomService.createRoom(dto));
//    }
//    @PutMapping("/update-room/{roomId}")
//    public ResponseEntity<?> upadteRoom(@PathVariable String roomId, @RequestBody RoomRequest dto){
//        return ResponseEntity.ok(roomService.updateRoom(roomId, dto));
//    }
//    @PostMapping("/all-rooms")
//    public ResponseEntity<?> listAllRooms(@RequestBody FetchAPIRequest fetchAPIRequest){
//        return ResponseEntity.ok(this.roomService.getAllRooms(fetchAPIRequest));
//    }
//    @DeleteMapping("/delete-rooms/{roomId}")
//    public ResponseEntity<?> deleteRooms(@PathVariable("roomId") String roomId){
//        DeleteRequest delete = roomService.deleteRoom(roomId);
//        return ResponseEntity.ok(delete);
//    }
//    @PostMapping("/rooms/{pgId}")
//    public ResponseEntity<APIResponse> getRoomsByPg(
//            @PathVariable String pgId,
//            @RequestBody FetchAPIRequest fetchAPIRequest) {
//        return ResponseEntity.ok(this.roomService.roomByPg(pgId, fetchAPIRequest));
//    }


}
