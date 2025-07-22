package com.thryve.pgfinder.dto.request;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGRequest {
    private String name;
    private String address;
    private String description;
    private String imageUrl;
    private String userId;
}
