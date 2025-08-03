package com.thryve.pgfinder.repository;

import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomReposoitory extends JpaRepository<Room, String>, JpaSpecificationExecutor<Room> {

    @Query("SELECT r FROM Room r WHERE LOWER(TRIM(r.sharing)) = LOWER(TRIM(:sharing)) AND r.isAc = :isAc")
    Optional<Room> findBySharingAndIsAc(@Param("sharing") String sharing, @Param("isAc") boolean isAc);
}
