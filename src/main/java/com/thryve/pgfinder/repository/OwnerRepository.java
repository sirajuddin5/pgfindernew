package com.thryve.pgfinder.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.thryve.pgfinder.model.Owner;

public interface OwnerRepository extends JpaRepository<Owner, UUID> ,JpaSpecificationExecutor<Owner>{

	 Optional<Owner> findByAuthUserId(String authUserId);
}
