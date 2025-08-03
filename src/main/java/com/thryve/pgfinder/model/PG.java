package com.thryve.pgfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "pgs", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "address"})
})


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PG {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private String address;
    private String description;
    private String imageUrl;
    private String userId;

}
