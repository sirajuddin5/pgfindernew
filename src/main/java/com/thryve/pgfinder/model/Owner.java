package com.thryve.pgfinder.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "owner",
       indexes = {@Index(columnList = "email"), @Index(columnList = "phone")},
       uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "phone"})}
	  )
@Getter
@Setter
@NoArgsConstructor

@Builder
public class Owner extends BaseEntity {
	
//	@Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private String ownerId;
	
	@Column(name = "full_name", nullable = false, length = 150)
	@NotBlank
	private String fullName;
	
    @Column(name = "email", length = 150, unique = true)
    @Email
    private String email;

    @Column(name = "phone", length = 30)
    private String phone;
    

    // link to the authentication user id (if using separate auth microservice)
    @Column(name = "auth_user_id", length = 100)
    private String authUserId;
    
    @Embedded
    private AuditModel audit = new AuditModel();
    
    @Builder
    public Owner(String fullName, String email, String phone, String authUserId, AuditModel audit) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.authUserId = authUserId;
        this.audit = (audit != null) ? audit : new AuditModel();
    }

}
