package com.thryve.pgfinder.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet; 


import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.BaseEntity;
import com.thryve.pgfinder.model.embedded.Address;
import com.thryve.pgfinder.model.embedded.GeoLocation;
import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.AvailabilityStatus;
import com.thryve.pgfinder.model.enums.GenderPreference;
import com.thryve.pgfinder.model.enums.PgType;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "pgs",
		indexes = {
	        @Index(columnList = "city"),
	        @Index(columnList = "state"),
	        @Index(columnList = "created_at"),
	        @Index(columnList = "availability_status")}, 
		uniqueConstraints = {
				@UniqueConstraint(columnNames = {"name", "address"})
})


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PG extends BaseEntity {

    
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long pgId;
    
    
    @Column(name = "name", nullable = false, length = 150)
    @NotBlank
    private String name;
    
    @Column(name = "short_description", length = 500)
    private String shortDescription;
    

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pg_type", length = 30)
    private PgType pgType;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender_preference", length = 30)
    private GenderPreference genderPreference = GenderPreference.ANY;
    
    @Embedded
    private Address address;
    
    @Embedded
    private GeoLocation geoLocation;
    
 // owner relation (Many PGs per owner)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;
    
 // One PG has multiple rooms (units)
    @OneToMany(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Room> rooms = new ArrayList<>();

    // Images, reviews
    @OneToMany(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<PgImage> images = new ArrayList<>();
    
    @OneToMany(mappedBy = "pg", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();
    
 // Amenities as element collection for quick querying and flexible additions
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pg_amenity", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "amenity", length = 50)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<AmenityType> amenities = new HashSet<>();
    
 // Summary pricing fields (e.g. average price) and currency
    @Column(name = "base_price", precision = 12, scale = 2)
    private BigDecimal basePrice;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "availability_status", length = 30)
    private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;
    
 // metadata and flags
    @Column(name = "is_verified", nullable = false)
    private boolean verified = false;
    
    @Column(name = "is_featured", nullable = false)
    private boolean featured = false;

    @Column(name = "max_occupancy")
    private Integer maxOccupancy;

    @Column(name = "contact_phone", length = 30)
    private String contactPhone;

    @Column(name = "contact_email", length = 150)
    private String contactEmail;
    
 // drop-in quick search tags
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "pg_tags", joinColumns = @JoinColumn(name = "pg_id"))
    @Column(name = "tag", length = 50)
    @Builder.Default
    private Set<String> tags = new HashSet<>();
    
 // Audit fields
   
    
    @Embedded
    @Builder.Default
    private AuditModel audit = new AuditModel();
    
    @PrePersist
    public void ensureAuditNotNull() {
        System.out.println("=== STEP 3: PG.prePersist() ===");
        System.out.println("Before ensureAuditNotNull: " + audit);
        if (audit == null) {
            audit = new AuditModel();
            System.out.println("Audit was NULL → creating new AuditModel()");
        }
        System.out.println("After ensureAuditNotNull: " + audit);
    }

    
    
   
    
    // convenience helper to add room
    public void addRoom(Room r) {
        r.setPg(this);
        this.rooms.add(r);
    }

    public void removeRoom(Room r) {
        r.setPg(null);
        this.rooms.remove(r);
    }
    
//    private String name;
//    private String address;
//    private String description;
//    private String imageUrl;
//    private String userId;
    
    
    @Builder
    public PG(String name,
              String shortDescription,
              String description,
              PgType pgType,
              GenderPreference genderPreference,
              Address address,
              GeoLocation geoLocation,
              Owner owner,
              BigDecimal basePrice,
              AvailabilityStatus availabilityStatus,
              boolean verified,
              boolean featured,
              Integer maxOccupancy,
              String contactPhone,
              String contactEmail,
              Set<AmenityType> amenities,
              Set<String> tags,
              AuditModel audit) {

        this.name = name;
        this.shortDescription = shortDescription;
        this.description = description;
        this.pgType = pgType;
        this.genderPreference = genderPreference != null ? genderPreference : GenderPreference.ANY;
        this.address = address;
        this.geoLocation = geoLocation;
        this.owner = owner;
        this.basePrice = basePrice;
        this.availabilityStatus = availabilityStatus != null ? availabilityStatus : AvailabilityStatus.AVAILABLE;
        this.verified = verified;
        this.featured = featured;
        this.maxOccupancy = maxOccupancy;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
        this.amenities = amenities != null ? amenities : new HashSet<>();
        this.tags = tags != null ? tags : new HashSet<>();
        this.audit = audit != null ? audit : new AuditModel();

        // initialize empty collections
        this.rooms = new ArrayList<>();
        this.images = new ArrayList<>();
        this.reviews = new ArrayList<>();
    }

}
