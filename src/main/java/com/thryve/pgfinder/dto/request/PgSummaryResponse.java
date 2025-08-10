package com.thryve.pgfinder.dto.request;

import java.math.BigDecimal;
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
public class PgSummaryResponse {

    private UUID id;
    private String name;
    private String shortDescription;
    private Address address;
    private GeoLocation geoLocation;
    private BigDecimal basePrice;
//    private Currency currency;
    private AvailabilityStatus availabilityStatus;
    private PgType pgType;
    private Set<AmenityType> amenities;
    private String thumbnailImageUrl; // first image if exists
    private Double avgRating;
    private Integer reviewCount;
    private Boolean verified;
}
