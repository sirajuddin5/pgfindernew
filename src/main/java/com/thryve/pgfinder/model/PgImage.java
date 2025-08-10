package com.thryve.pgfinder.model;

import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.BaseEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "pg_image",
       indexes = {@Index(columnList = "pg_id"), @Index(columnList = "storage_key")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PgImage extends BaseEntity {
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "image_id")
//    private String imageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id")
    private PG pg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room; // optional: image for a room

    @Column(name = "url", length = 1000, nullable = false)
    private String url;

    @Column(name = "storage_key", length = 500)
    private String storageKey; // e.g. S3 key

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Embedded
    private AuditModel audit = new AuditModel();
}
