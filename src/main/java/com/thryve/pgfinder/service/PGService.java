package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.model.PG;

import java.util.List;

public interface PGService {
    PGResponse createPG(PGRequest dto);
    List<PGResponse> getPGsByUser(String userId);
    PGResponse updatePG( PGRequest dto);
    List<PG> getAllPgs();
}

