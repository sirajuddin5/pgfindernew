package com.thryve.pgfinder.model;

import java.time.OffsetDateTime;

import com.thryve.pgfinder.model.common.BaseEntity;
import com.thryve.pgfinder.model.enums.AvailabilityStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "availability_slot", indexes = {@Index(columnList = "room_id"), @Index(columnList = "pg_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilitySlot extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id")
    private PG pg;

    @Column(name = "from_date")
    private OffsetDateTime from;

    @Column(name = "to_date")
    private OffsetDateTime to;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private AvailabilityStatus status = AvailabilityStatus.AVAILABLE;

    @Column(name = "notes", length = 400)
    private String notes;
}

