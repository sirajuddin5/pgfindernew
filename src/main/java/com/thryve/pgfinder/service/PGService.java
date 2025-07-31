package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

import java.util.List;

public interface PGService {
    APIResponse createPG(PGRequest pgRequest);
    List<PGResponse> getPGsByUser(String userId);
    PGResponse updatePG( PGRequest dto);
    APIResponse fetchAll(FetchAPIRequest fetchAPIRequest);
}

