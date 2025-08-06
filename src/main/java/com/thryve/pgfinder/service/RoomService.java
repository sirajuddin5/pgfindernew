package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.RoomRequest;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

public interface RoomService {
    APIResponse createRoom(RoomRequest roomRequest);
    APIResponse updateRoom( String roomId, RoomRequest roomRequest);
    APIResponse getAllRooms(FetchAPIRequest fetchAPIRequest);
    DeleteRequest deleteRoom(String roomId);

    APIResponse roomByPg(String pgId, FetchAPIRequest fetchAPIRequest);
}
