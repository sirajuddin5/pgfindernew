package com.thryve.pgfinder.repository;

import com.thryve.pgfinder.model.PG;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface PGRepository extends JpaRepository<PG, Long> {
    List<PG> findByUserId(String userId);
}
