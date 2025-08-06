package com.thryve.pgfinder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "rooms", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"pg_id", "sharing", "is_ac"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String imageUrl;
    private String sharing;
    private boolean isAc;
    private String description;
    private double price;

//    private String pgId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id")
@JsonIgnore
//    @JoinColumn(name = "pgId", referencedColumnName = "id", insertable = false, updatable = false)
    private PG pg;

}
