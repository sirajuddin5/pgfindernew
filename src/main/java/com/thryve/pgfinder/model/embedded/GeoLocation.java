package com.thryve.pgfinder.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeoLocation {
	
	private Double latitude;
    private Double longitude;

    // optional place id from a map provider for quick lookups
    private String placeId;

}
