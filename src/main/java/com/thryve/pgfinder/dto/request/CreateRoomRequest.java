package com.thryve.pgfinder.dto.request;

import java.math.BigDecimal;
import java.util.Set;

import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.RoomType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CreateRoomRequest {

    @NotBlank
    private String title;

    @NotNull
    private RoomType roomType;

    @Min(1)
    private Integer capacity = 1;

    @DecimalMin("0.0")
    private BigDecimal price;

//    private BookingFrequency priceFrequency = BookingFrequency.MONTHLY;

    private Integer availableUnits = 1;

    private String description;

    private Set<AmenityType> amenities;
}
