package com.thryve.pgfinder.controller;

import com.thryve.pgfinder.dto.request.CreatePgRequest;
import com.thryve.pgfinder.dto.request.CreateRoomRequest;
import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.dto.response.PgDetailResponse;
import com.thryve.pgfinder.exception.AlreadyExistException;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

import com.thryve.pgfinder.service.PGService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
public class PGController {
	@Autowired
    private final PGService pgService;

    @PostMapping("/create-pg")
    public  ResponseEntity<?>  create(@RequestBody CreatePgRequest dto){
        return ResponseEntity.ok(this.pgService.createPg(dto));
    }
    
    @PostMapping("/get-pg/{id}")
    public  ResponseEntity<?>  getPgwithId(@PathVariable("id") UUID id){
        return ResponseEntity.ok(this.pgService.getPgById(id));
    }
    
    @PutMapping("/update-pg/{id}")
    public  ResponseEntity<?>  updatePg(@PathVariable("id") UUID id,@Valid @RequestBody UpdatePgRequest req){
    	return ResponseEntity.ok(this.pgService.updatePg(id, req));
    }
    
    @PostMapping("/getAll-pg")
    public  ResponseEntity<?>  getAllPgs(@RequestBody FetchAPIRequest fetchAPIRequest){
        return ResponseEntity.ok(this.pgService.listPgs(fetchAPIRequest));
    }
    
    @DeleteMapping("/delete-pg/{id}")
    public  ResponseEntity<?>  deletePg(@PathVariable("id") UUID id){
    	return ResponseEntity.ok(this.pgService.deletePg(id));
    }
    
    @PostMapping("/addRooms-pg/{id}")
    public  ResponseEntity<?>  addRoomsToPg(@PathVariable("id") UUID id, @Valid @RequestBody CreateRoomRequest req){
        return ResponseEntity.ok(this.pgService.addRoom(id,req));
    }
    
    @PutMapping("/update-room/{id}")
    public  ResponseEntity<?>  updateRoom(@PathVariable("id") UUID id,@Valid @RequestBody CreateRoomRequest req){
    	return ResponseEntity.ok(this.pgService.updateRoom(id, req));
    }
    
    @DeleteMapping("/delete-room/{id}")
    public  ResponseEntity<?>  deleteRoom(@PathVariable("id") UUID id){
    	return ResponseEntity.ok(this.pgService.deleteRoom(id));
    }

//    @PostMapping("/all-pgs")
//    public ResponseEntity<?> ListAllpG(@RequestBody FetchAPIRequest fetchAPIRequest){
//        return ResponseEntity.ok(this.pgService.fetchAll(fetchAPIRequest));
//    }

//    @PostMapping("/owned-pgs/{userId}")
//    public ResponseEntity<Object> list(@PathVariable String userId, @RequestBody FetchAPIRequest fetchAPIRequest) {
//        return ResponseEntity.ok(this.pgService.getPGsByUser(userId, fetchAPIRequest));
//    }


//    @PutMapping("/update-pg/{pgId}")
//    public ResponseEntity<?> update(@PathVariable String pgId, @RequestBody PGRequest dto) {
////        PGResponse response = pgService.updatePG(String pgId, PGRequest dto);
////        return PGResponseHandler.ResponseBuilder("PG Updated", HttpStatus.OK, response);
//        return ResponseEntity.ok(this.pgService.updatePG(pgId, dto));
//    }
    
//	@DeleteMapping("/delete-pg/{pgId}")
//	public ResponseEntity<DeleteRequest> deletePG(@PathVariable("pgId") String pgID){
//		DeleteRequest deleted = pgService.deletePG(pgID);
//	return ResponseEntity.ok(deleted);
//	}
	
    @ExceptionHandler(AlreadyExistException.class)
        public ResponseEntity<Object> handleDuplicatePG(AlreadyExistException ex){
            Map<String, Object> body = new HashMap<>();
            body.put("status", HttpStatus.CONFLICT.value());
            body.put("message", ex.getMessage());
            return new ResponseEntity<>(body, HttpStatus.CONFLICT);

    }

}
