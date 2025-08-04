package com.thryve.pgfinder.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomRequest {
    private String pgId;             // FK to PG (optional: @ManyToOne)
    private String imageUrl;
    private String sharing;
    private boolean isAc;
    private String description;
    private double price;
}
