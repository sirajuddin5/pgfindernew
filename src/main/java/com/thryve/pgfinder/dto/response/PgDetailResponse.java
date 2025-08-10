package com.thryve.pgfinder.dto.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.thryve.pgfinder.model.embedded.Address;
import com.thryve.pgfinder.model.embedded.GeoLocation;
import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.AvailabilityStatus;
import com.thryve.pgfinder.model.enums.PgType;

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
public class PgDetailResponse {

    private UUID id;
    private String name;
    private String shortDescription;
    private String description;
    private PgType pgType;
    private Address address;
    private GeoLocation geoLocation;
    private String contactPhone;
    private String contactEmail;
    private Set<AmenityType> amenities;
//    private Currency currency;
    private BigDecimal basePrice;
    private AvailabilityStatus availabilityStatus;
    private Boolean verified;
    private Boolean featured;
    private Integer maxOccupancy;
    private List<String> tags;
    private List<RoomResponse> rooms;
    private List<ImageResponse> images;
    private List<ReviewResponse> reviews;
    private OwnerDto owner;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
}