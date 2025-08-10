//package com.thryve.pgfinder.service.Impl;
//
//import com.thryve.pgfinder.config.validation.UtilityValidation;
//import com.thryve.pgfinder.dto.request.RoomRequest;
//import com.thryve.pgfinder.exception.ResourceNotFoundException;
//import com.thryve.pgfinder.model.PG;
//import com.thryve.pgfinder.model.Room;
//import com.thryve.pgfinder.model.common.APIResponse;
//import com.thryve.pgfinder.model.common.DeleteRequest;
//import com.thryve.pgfinder.model.common.FetchAPIRequest;
//import com.thryve.pgfinder.model.common.filter.specification.FiltersSpecification;
//import com.thryve.pgfinder.model.common.page.PageRequestDTO;
//import com.thryve.pgfinder.repository.PGRepository;
//import com.thryve.pgfinder.repository.RoomRepository;
//import com.thryve.pgfinder.service.RoomService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.stereotype.Service;
//
//import java.lang.reflect.Field;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class RoomServiceImpl implements RoomService {
//
//    @Autowired
//    private final RoomRepository roomRepository;
//
//    @Autowired
//    private final UtilityValidation utilityValidation;
//
//    @Autowired
//    private final FiltersSpecification<Room> roomFiltersSpecification;
//
//    @Autowired
//    private final PGRepository pgRepository;
//
//    // create room 
//    @Override
//    public APIResponse createRoom(RoomRequest roomRequest) {
//       
//        APIResponse response = new APIResponse();
//
//        try {
//            PG pg = pgRepository.findById(roomRequest.getPgId())
//                    .orElseThrow(() -> new RuntimeException("PG not found"));
//
//            Map<String, Object> fieldsMap = new HashMap<>();
//            for (Field field : RoomRequest.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                fieldsMap.put(field.getName(), field.get(roomRequest));
//            }
//
//            Map<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
//            if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
//                String validationMessage = validationResponse.get("message") != null
//                        ? validationResponse.get("message").toString()
//                        : "Validation failed";
//                response.setMessage(validationMessage);
//                response.setStatus("error");
//                return response;
//            }
//            String sharing = roomRequest.getSharing().trim().toLowerCase();
//            boolean isAc = roomRequest.isAc();
//
//            Optional<Room> existingRoom = roomRepository.findBySharingAndIsAcAndPgId(sharing, isAc, roomRequest.getPgId());
//            if (existingRoom.isPresent()) {
//                response.setMessage("This Room is already active.");
//                response.setStatus("error");
//                return response;
//            }
//
//            Room room = new Room();
////              pg.setId(pgRequest.getUserId());
//            room.setAc(roomRequest.isAc());
//            room.setSharing(roomRequest.getSharing());
//            room.setPrice(roomRequest.getPrice());
//            room.setDescription(roomRequest.getDescription());
//            room.setImageUrl(roomRequest.getImageUrl());
//            room.setPg(pg);
//
//
//            Room savedRoom = this.roomRepository.save(room);
//            response.setResult(savedRoom);
//            response.setMessage("Room Added Succesfully");
//            response.setStatus("success");;
//
//        } catch (Exception e) {
//            response.setMessage(e.getMessage());
//            response.setStatus("error");
//        }
//
//        return response;
//    }
////
//    
//    // update Room 
////    @SneakyThrows
//    @Override
//    public APIResponse updateRoom(String roomId, RoomRequest roomRequest) {
//        APIResponse response = new APIResponse();
//        try {
//            // Validate PG existence
//            PG pg = pgRepository.findById(roomRequest.getPgId())
//                    .orElseThrow(() -> new ResourceNotFoundException("PG not found with id: " + roomRequest.getPgId()));
//
//            // Reflective Validation
//            Map<String, Object> fieldsMap = new HashMap<>();
//            for (Field field : RoomRequest.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                fieldsMap.put(field.getName(), field.get(roomRequest));
//            }
//
//            Map<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
//            if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
//                String validationMessage = validationResponse.get("message") != null
//                        ? validationResponse.get("message").toString()
//                        : "Validation failed";
//                response.setMessage(validationMessage);
//                response.setStatus("error");
//                return response;
//            }
//
//            // Fetch existing room by ID
//            Room existingRoom = roomRepository.findById(roomId)
//                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with id: " + roomId));
//
//            // Check for duplicate room with same config
//            Optional<Room> duplicateRoom = roomRepository.findBySharingAndIsAcAndPgId(
//                    roomRequest.getSharing().trim().toLowerCase(),
//                    roomRequest.isAc(),
//                    roomRequest.getPgId()
//            );
//            if (duplicateRoom.isPresent() && !duplicateRoom.get().getId().equals(roomId)) {
//                response.setMessage("Room with the same sharing and AC configuration already exists in this PG.");
//                response.setStatus("error");
//                return response;
//            }
//
//            // Update fields
//            existingRoom.setPg(pg);
//            existingRoom.setAc(roomRequest.isAc());
//            existingRoom.setPrice(roomRequest.getPrice());
//            existingRoom.setDescription(roomRequest.getDescription());
//            existingRoom.setSharing(roomRequest.getSharing());
//            existingRoom.setImageUrl(roomRequest.getImageUrl());
//
//            // Save updated room
//            Room savedRoom = this.roomRepository.save(existingRoom);
//            response.setResult(savedRoom);
//            response.setMessage("Room updated successfully");
//            response.setStatus("success");
//
//        } catch (Exception e) {
//            response.setMessage("Error: " + e.getMessage());
//            response.setStatus("error");
//        }
//
//        return response;
//    }
//
//
//
////
////    @Override
////    public APIResponse roomByPg(RoomRequest roomRequest) {
////        return null;
////    }
//
//    @Override
////    public APIResponse getAllRooms(FetchAPIRequest fetchAPIRequest) {
////        APIResponse response = new APIResponse();
////        try{
////            Specification<Room> searchSpecification = this.roomFiltersSpecification.getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
////            Pageable pageable = (new PageRequestDTO()).getPageable(fetchAPIRequest.getPageRequestDTO());
////            Page<Room> cameraDetailsPage = this.roomRepository.findAll(searchSpecification, pageable);
////            response.setResult(cameraDetailsPage);
////            response.setMessage("room's Details Fetched Successfully.");
////            response.setStatus("success");
////        } catch (Exception e) {
////            e.printStackTrace();
////            response.setMessage(e.getMessage());
////            response.setStatus("error");
////        }
////        return response;
////    }
//    
//    
//    // get All Rooms 
//
//    public APIResponse getAllRooms(FetchAPIRequest fetchAPIRequest) {
//        APIResponse response = new APIResponse();
//
//        try {
//            Specification<Room> searchSpecification = this.roomFiltersSpecification.getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
//            Pageable pageable = (new PageRequestDTO()).getPageable(fetchAPIRequest.getPageRequestDTO());
//            Page<Room> cameraDetailsPage = this.roomRepository.findAll(searchSpecification, pageable);
//            response.setResult(cameraDetailsPage);
//            response.setMessage("pg's Details Fetched Successfully.");
//            response.setStatus("success");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMessage(e.getMessage());
//            response.setStatus("error");
//        }
//
//
//        return response;
//    }
////
//    
//    // Delete Rooms 
//    @Override
//    public DeleteRequest deleteRoom(String roomId) {
//        roomRepository.deleteById(roomId);
//        return new DeleteRequest(roomId);
//    }
//
//    // Room By PG
//    @Override
//    public APIResponse roomByPg(String pgId, FetchAPIRequest fetchAPIRequest){
//        APIResponse response = new APIResponse();
//        try{
////        List<Room> rooms = this.roomRepository.findByPgId(pgId);
////        response.setResult(rooms);
////        response.setMessage("Rooms fetched successfully for PG ID: " + pgId);
////        response.setStatus("success");
//        Specification<Room> searchSpecification = this.roomFiltersSpecification
//                .getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
//        Specification<Room> pgIdSpec = (root, query, criteriaBuilder) ->
//                criteriaBuilder.equal(root.get("pg").get("id"), pgId);        Specification<Room> finalSpec = (searchSpecification == null)
//                ? pgIdSpec
//                :searchSpecification.and(pgIdSpec);
//        Pageable pageable = new PageRequestDTO().getPageable(fetchAPIRequest.getPageRequestDTO());
//        Page<Room> roomPage = roomRepository.findAll(finalSpec, pageable);
//        response.setResult(roomPage);
//        response.setMessage("Rooms fetched successfully for PG ID: " + pgId);
//        response.setStatus("success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setStatus("error");
//            response.setMessage("Failed to fetching rooms: " + e.getMessage());
//        }
//        return response;
//    }
//}
