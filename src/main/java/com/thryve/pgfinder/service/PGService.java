package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.FetchAPIRequest;

import java.util.List;

public interface PGService {
    APIResponse createPG(PGRequest pgRequest);

    APIResponse updatePG(String pgId, PGRequest pgRequest);

    List<PGResponse> getPGsByUser(String userId);
//    PGResponse updatePG( PGRequest dto);

//    APIResponse updatePg(String pgId, PGRequest pgRequest);

    APIResponse fetchAll(FetchAPIRequest fetchAPIRequest);
    String deletePG(String pgId);
}

