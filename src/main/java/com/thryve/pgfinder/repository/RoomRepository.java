package com.thryve.pgfinder.repository;

import com.thryve.pgfinder.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {

//    @Query("SELECT r FROM Room r WHERE LOWER(TRIM(r.sharing)) = LOWER(TRIM(:sharing)) AND r.isAc = :isAc AND r.pg.id = :pgId")
//    Optional<Room> findBySharingAndIsAcAndPgId(
//            @Param("sharing") String sharing,
//            @Param("isAc") boolean isAc,
//            @Param("pgId") UUID pgId
//    );
    List<Room> findByPgId(UUID pgId);
    
    List<Room> findByPgIdAndAuditDeletedFalse(UUID pgId);

}
