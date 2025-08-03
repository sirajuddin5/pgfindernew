package com.thryve.pgfinder.repository;

import com.thryve.pgfinder.model.PG;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface PGRepository extends JpaRepository<PG, String> ,JpaSpecificationExecutor<PG> {

    @Query("SELECT p FROM PG p WHERE LOWER(TRIM(p.name)) = LOWER(TRIM(:name)) AND LOWER(TRIM(p.address)) = LOWER(TRIM(:address))")
    Optional<PG> findByNameAndAddress(@Param("name") String name, @Param("address") String address);
}
