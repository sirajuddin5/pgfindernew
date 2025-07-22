package com.thryve.pgfinder.service.Impl;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.mapper.PGMapper;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.repository.PGRepository;
import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PGServiceImpl implements PGService {

    private final PGRepository pgRepository;

    @Override
    public PGResponse createPG(PGRequest dto) {
        PG pg = PGMapper.toEntity(dto);
        PG savedPG = pgRepository.save(pg);
        return PGMapper.toDto(savedPG);
    }

    @Override
    public List<PGResponse> getPGsByUser(String userId) {
        return pgRepository.findByUserId(userId)
                .stream()
                .map(PGMapper::toDto)
                .collect(Collectors.toList());
    }
}
