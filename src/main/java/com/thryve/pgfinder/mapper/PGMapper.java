package com.thryve.pgfinder.mapper;

import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.model.PG;

public class PGMapper {

    public static PG toEntity(PGRequest dto) {
        PG pg = new PG();
        pg.setName(dto.getName());
        pg.setAddress(dto.getAddress());
        pg.setDescription(dto.getDescription());
        pg.setImageUrl(dto.getImageUrl());
        pg.setUserId(dto.getUserId());
        return pg;
    }

    public static PGResponse toDto(PG pg) {
        return new PGResponse(
                pg.getId(),
                pg.getName(),
                pg.getAddress(),
                pg.getDescription(),
                pg.getImageUrl(),
                pg.getUserId()
        );
    }

}
