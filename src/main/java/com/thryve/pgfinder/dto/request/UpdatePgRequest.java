package com.thryve.pgfinder.dto.request;

import java.util.Set;

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
public class UpdatePgRequest {

    @Size(max = 150)
    private String name;

    @Size(max = 500)
    private String shortDescription;

    private String description;

    private String contactEmail;

    private String contactPhone;

    private Set<String> tags;

    // toggles
    private Boolean verified;

    private Boolean featured;
}

