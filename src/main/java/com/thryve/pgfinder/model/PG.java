package com.thryve.pgfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pgs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String description;
    private String imageUrl;
    private String userId;
}
