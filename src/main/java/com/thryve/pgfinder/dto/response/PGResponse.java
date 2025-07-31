package com.thryve.pgfinder.dto.response;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PGResponse {
    private String pgId;
    private String name;
    private String address;
    private String description;
    private String imageUrl;
    private String userId;
}
// need to write a function pg create once name and adddress should be different before creation