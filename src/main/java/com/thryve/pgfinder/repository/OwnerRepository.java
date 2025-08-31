package com.thryve.pgfinder.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.thryve.pgfinder.model.Owner;
import java.util.List;


public interface OwnerRepository extends JpaRepository<Owner, UUID> ,JpaSpecificationExecutor<Owner>{

	 Optional<Owner> findByAuthUserId(String authUserId);
	 
	 Optional<Owner> findByUsername(String username);
}
