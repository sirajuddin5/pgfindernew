package com.thryve.pgfinder.model.common;

import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class AuditModel {
	
//	@Column(name = "created_at", nullable = false, updatable = false)
//	@Column(name = "created_at", insertable = false, updatable = false,
//    columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	@Column(name = "created_at", nullable = false, updatable = false,
	        columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private OffsetDateTime createdAt;
	
	@Column(name = "updated_at")
	private OffsetDateTime updatedAt;
	
	@Column(name = "created_by", length = 100)
	private String createdBy;
	
	@Column(name = "updated_by", length = 100)
	private String updatedBy;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted = false;
	
	@Column(name = "deleted_at")
	private OffsetDateTime deletedAt;
	
//	@PrePersist
//    public void prePersist() {
//        createdAt = OffsetDateTime.now();
//    }
//
//    @PreUpdate
//    public void preUpdate() {
//        updatedAt = OffsetDateTime.now();
//    }

//	@PrePersist
//    public void prePersist() {
////        if (createdAt == null) {
////            createdAt = OffsetDateTime.now();
////        }
////        
////        deleted = false;
//		
//		 System.out.println(">>> AuditModel.prePersist() called. Deleted before = " + deleted);
//		    if (createdAt == null) {
//		        createdAt = OffsetDateTime.now();
//		    }
//		    deleted = false;
//		    System.out.println(">>> AuditModel.prePersist() exiting. Deleted after = " + deleted);
//
//       
//    }
	@PrePersist
	public void prePersist() {
	    System.out.println("=== STEP 4: AuditModel.prePersist() ===");
	    System.out.println("Before: deleted = " + deleted);
	    if (createdAt == null) {
	        createdAt = OffsetDateTime.now();
	    }
	    deleted = false;
	    System.out.println("After: deleted = " + deleted);
	}

	

    @PreUpdate
    public void preUpdate() {
        updatedAt = OffsetDateTime.now();
    }
}
