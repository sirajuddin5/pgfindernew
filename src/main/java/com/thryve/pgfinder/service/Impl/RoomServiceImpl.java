package com.thryve.pgfinder.service.Impl;

import com.thryve.pgfinder.config.validation.UtilityValidation;
import com.thryve.pgfinder.dto.request.RoomRequest;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.Room;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;
import com.thryve.pgfinder.model.common.filter.specification.FiltersSpecification;
import com.thryve.pgfinder.repository.PGRepository;
import com.thryve.pgfinder.repository.RoomReposoitory;
import com.thryve.pgfinder.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    @Autowired
    private final RoomReposoitory roomReposoitory;

    @Autowired
    private final UtilityValidation utilityValidation;

    @Autowired
    private final FiltersSpecification<Room> roomFiltersSpecification;

    @Autowired
    private final PGRepository pgRepository;

    @Override
    public APIResponse createRoom(RoomRequest roomRequest) {
        // Check if PG exists

        APIResponse response = new APIResponse();

        try {
            PG pg = pgRepository.findById(roomRequest.getPgId())
                    .orElseThrow(() -> new RuntimeException("PG not found"));

            HashMap<String, Object> fieldsMap = new HashMap();
            Class<?> clazz = RoomRequest.class;
            List<String> bypassList = Arrays.asList();

            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (!bypassList.contains(field.getName())) {
                    fieldsMap.put(field.getName(), field.get(roomRequest));
                }
            }

            HashMap<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
            if ((Boolean)validationResponse.get("status")) {
                response.setMessage(validationResponse.get("message").toString());
                response.setStatus("error");
                return response;
            }
            String sharing = roomRequest.getSharing().trim().toLowerCase();
            boolean isAc = roomRequest.isAc();

            Optional<Room> existingRoom = roomReposoitory.findBySharingAndIsAcAndPgId(sharing, isAc, roomRequest.getPgId());
            if (existingRoom.isPresent()) {
                response.setMessage("This Room is already active.");
                response.setStatus("error");
                return response;
            }

            Room room = new Room();
//              pg.setId(pgRequest.getUserId());
            room.setAc(roomRequest.isAc());
            room.setSharing(roomRequest.getSharing());
            room.setPrice(roomRequest.getPrice());
            room.setDescription(roomRequest.getDescription());
            room.setImageUrl(roomRequest.getImageUrl());
            room.setPg(pg);


            Room savedRoom = this.roomReposoitory.save(room);
            response.setResult(savedRoom);
            response.setMessage("Room Added Succesfully");
            response.setStatus("success");;

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus("error");
        }

        return response;
    }
//
//    @Override
//    public APIResponse updateRoom(RoomRequest roomRequest) {
//        return null;
//    }
//
//    @Override
//    public APIResponse roomByPg(RoomRequest roomRequest) {
//        return null;
//    }

//    @Override
//    public APIResponse getAllRooms(FetchAPIRequest fetchAPIRequest) {
//        return null;
//    }
//
//    @Override
//    public DeleteRequest deleteRoom(String roomId) {
//        return null;
//    }
}
