package com.thryve.pgfinder.service;

import java.util.UUID;

import com.thryve.pgfinder.dto.request.CreateImagePgRequest;
import com.thryve.pgfinder.dto.request.CreatePgRequest;
import com.thryve.pgfinder.dto.request.CreateRoomRequest;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

public interface PgImageService {

	
		APIResponse createPgImage(CreateImagePgRequest request);  // in result add PgDetailResponse
		
		APIResponse updatePgImage(UUID pgId, UpdatePgRequest request); // in result add PgDetailResponse
		
		APIResponse getImagePgById(UUID pgId); // in result add PgDetailResponse
		
		APIResponse listAllImagesOfPgs(FetchAPIRequest fetchAPIRequest); // in result add Page<PgSummaryResponse>
		
		APIResponse deleteImageOfPg(UUID pgId,UUID imageId); // soft delete  
	
}

