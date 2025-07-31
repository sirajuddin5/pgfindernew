package com.thryve.pgfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String pgId;             // FK to PG (optional: @ManyToOne)
    private String imageUrl;
    private String sharing;
    private boolean isAc;
    private String description;
    private double price;
}
