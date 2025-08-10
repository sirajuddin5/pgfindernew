package com.thryve.pgfinder.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.HashSet;

import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.BaseEntity;
import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.RoomType;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(
		name = "rooms", 
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"pg_id", "sharing", "is_ac"})},
		indexes = {
				@Index(columnList = "pg_id"), @Index(columnList = "room_type")}
	  )
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Room extends BaseEntity{
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String roomId;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pg_id", nullable = false)
    private PG pg;
    
    @Column(name = "title", length = 150)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", length = 30)
    private RoomType roomType;
    
    // capacity (beds/people)
    @Column(name = "capacity")
    @Min(1)
    @Builder.Default
    private Integer capacity = 1;
    
    // price per booking frequency unit
    @Column(name = "price", precision = 12, scale = 2)
    private BigDecimal price;
    
    // per-room amenities override (subset of PG amenities)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "room_amenity", joinColumns = @JoinColumn(name = "room_id"))
    @Column(name = "amenity", length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<AmenityType> amenities = new HashSet<>();
    
    @Column(name = "available_units")
    private Integer availableUnits = 1;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
   
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
    
    @Embedded
    @Builder.Default
    private AuditModel audit = new AuditModel();

//    private String imageUrl;
//    private String sharing;
//    private boolean isAc;
//    private String description;
//    private double price;
//
////    private String pgId;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "pg_id")
//@JsonIgnore
////    @JoinColumn(name = "pgId", referencedColumnName = "id", insertable = false, updatable = false)
//    private PG pg;

}
