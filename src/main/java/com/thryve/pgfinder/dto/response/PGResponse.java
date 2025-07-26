package com.thryve.pgfinder.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGResponse {
    private String PGid;
    private String name;
    private String address;
    private String description;
    private String imageUrl;
    private String userId;
}
