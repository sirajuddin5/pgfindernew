package com.thryve.pgfinder.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.thryve.pgfinder.model.AvailabilitySlot;

public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, UUID>, JpaSpecificationExecutor<AvailabilitySlot>{

	 List<AvailabilitySlot> findByPgIdAndToAfterOrderByFromAsc(UUID pgId, java.time.OffsetDateTime after);
}
