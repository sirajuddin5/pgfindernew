package com.thryve.pgfinder.service.Impl;

import java.util.UUID;

import com.thryve.pgfinder.dto.request.CreateImagePgRequest;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.FetchAPIRequest;
import com.thryve.pgfinder.service.PgImageService;

public class PgImageServiceImpl implements PgImageService {

	@Override
	public APIResponse createPgImage(CreateImagePgRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse updatePgImage(UUID pgId, UpdatePgRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse getImagePgById(UUID pgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse listAllImagesOfPgs(FetchAPIRequest fetchAPIRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public APIResponse deleteImageOfPg(UUID pgId, UUID imageId) {
		// TODO Auto-generated method stub
		return null;
	}

}
