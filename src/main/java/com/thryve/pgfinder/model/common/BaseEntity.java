package com.thryve.pgfinder.model.common;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntity implements Serializable  {
	
	@Id
	@UuidGenerator
	@Column(name = "id", updatable = false, nullable = false,
			columnDefinition = "BINARY(16)")
	@JdbcTypeCode(SqlTypes.UUID)
	private UUID id;
	
	@Version
	@Column(name = "version")
	private Long version;

}
