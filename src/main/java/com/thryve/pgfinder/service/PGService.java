package com.thryve.pgfinder.service;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import java.util.List;

public interface PGService {
    PGResponse createPG(PGRequest dto);
    List<PGResponse> getPGsByUser(String userId);
}

