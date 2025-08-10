package com.thryve.pgfinder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.thryve.pgfinder.model.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID>, JpaSpecificationExecutor<Review>{

	List<Review> findByPgIdAndApprovedTrue(UUID pgId);
}
