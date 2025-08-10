package com.thryve.pgfinder.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewResponse {
    private UUID id;
    private String userId;
    private Integer rating;
    private String title;
    private String comment;
    private Boolean approved;
    private OffsetDateTime createdAt;
}