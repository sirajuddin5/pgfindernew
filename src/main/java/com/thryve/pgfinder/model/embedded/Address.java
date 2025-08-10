package com.thryve.pgfinder.model.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
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
public class Address {

	@NotBlank
	@Column(name = "line1", length = 255)
	private String line1;
	
	@Column(name = "line2", length = 255)
	private String line2;
	
	@NotBlank
    @Column(name = "city", length = 100)
	private String city;
	
	@NotBlank
    @Column(name = "state", length = 100)
	private String state;
	
	@NotBlank
    @Column(name = "country", length = 100)
	private String country;
	
	@Column(name = "postal_code", length = 20)
	private String postalCode;
	
}
