package com.thryve.pgfinder.model;

import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

//@Entity
//@Table(name = "reviews")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Review {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String reviewId;
//
//    private String roomId;
//    private String userId;
//    private String pgId;
//
//    private String comment;
//    private int rating;
//    private String date; // Optional: change to LocalDate for better type safety
//}

@Entity
@Table(name = "review", indexes = {@Index(columnList = "pg_id"), @Index(columnList = "user_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Review extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id")
    private PG pg;

    @Column(name = "user_id", length = 100, nullable = false)
    private String userId; // reference to Auth user id

    @Column(name = "rating", nullable = false)
    @Min(1)
    @Max(5)
    private Integer rating;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

    @Column(name = "approved")
    private boolean approved = false;

    @Embedded
    private AuditModel audit = new AuditModel();
}