package com.thryve.pgfinder.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import com.thryve.pgfinder.model.embedded.Address;
import com.thryve.pgfinder.model.embedded.GeoLocation;
import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.GenderPreference;
import com.thryve.pgfinder.model.enums.PgType;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class CreatePgRequest {

    @NotBlank
    @Size(max = 150)
    private String name;

    @Size(max = 500)
    private String shortDescription;

    private String description;

    @NotNull
    private PgType pgType;

    @NotNull
    private String ownerId; // owner auth id or reference
    
    @NotNull
    private GenderPreference genderPreference;

    @NotNull
    @Valid
    private Address address;

    @Valid
    private GeoLocation geoLocation;

//    @NotNull
//    private String ownerId; // owner auth id or reference

    // summary pricing
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal basePrice;

   
    @Size(max = 10)
    private Set<AmenityType> amenities;

    @Valid
    private List<CreateRoomRequest> rooms;

    // optional contact details
    @Email
    private String contactEmail;

    private String contactPhone;

    // tags used for search
    private Set<@Size(max = 50) String> tags;
}

