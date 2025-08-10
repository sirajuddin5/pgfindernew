package com.thryve.pgfinder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.thryve.pgfinder.model.PgImage;

public interface PgImageRepository extends JpaRepository<PgImage, UUID>, JpaSpecificationExecutor<PgImage>{

	 List<PgImage> findByPgIdOrderBySortOrderAsc(UUID pgId);
}
