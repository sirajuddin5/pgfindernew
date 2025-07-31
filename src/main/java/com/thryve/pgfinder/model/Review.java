package com.thryve.pgfinder.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String reviewId;

    private String roomId;
    private String userId;
    private String pgId;

    private String comment;
    private int rating;
    private String date; // Optional: change to LocalDate for better type safety
}
