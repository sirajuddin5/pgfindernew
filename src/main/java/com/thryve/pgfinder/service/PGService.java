package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.CreatePgRequest;
import com.thryve.pgfinder.dto.request.CreateRoomRequest;
import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface PGService {
//    APIResponse getPGsByUser(String userId, FetchAPIRequest fetchAPIRequest);
//
//    APIResponse createPG(PGRequest pgRequest);
//
//    APIResponse updatePG(String pgId, PGRequest pgRequest);
//
////    List<PGResponse> getPGsByUser(String userId);
////    PGResponse updatePG( PGRequest dto);
//
////    APIResponse updatePg(String pgId, PGRequest pgRequest);
//
//    APIResponse fetchAll(FetchAPIRequest fetchAPIRequest);
////    String deletePG(String pgId);
//    DeleteRequest deletePG(String pgId);
	
		APIResponse createPg(CreatePgRequest request);  // in result add PgDetailResponse
		
		APIResponse updatePg(UUID pgId, UpdatePgRequest request); // in result add PgDetailResponse
		
		APIResponse getPgById(UUID pgId); // in result add PgDetailResponse
		
		APIResponse listPgs(FetchAPIRequest fetchAPIRequest); // in result add Page<PgSummaryResponse>
		
		APIResponse deletePg(UUID pgId); // soft delete  
		
		APIResponse addRoom(UUID pgId, CreateRoomRequest req);  // in result add RoomResponse
		
		APIResponse updateRoom(UUID roomId, CreateRoomRequest req);  // in result add RoomResponse
		
		APIResponse deleteRoom(UUID roomId);
}

