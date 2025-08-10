package com.thryve.pgfinder.mapper;
//
//import com.thryve.pgfinder.dto.request.PGRequest;
//import com.thryve.pgfinder.dto.response.PGResponse;
//import com.thryve.pgfinder.model.PG;
//
//public class PGMapper {
//
//    public static PG toEntity(PGRequest dto) {
//        PG pg = new PG();
//        pg.setName(dto.getName());
//        pg.setAddress(dto.getAddress());
//        pg.setDescription(dto.getDescription());
//        pg.setImageUrl(dto.getImageUrl());
//        pg.setUserId(dto.getUserId());
//        return pg;
//    }
//
//    public static PGResponse toDto(PG pg) {
//        return new PGResponse(
//                pg.getId(),
//                pg.getName(),
//                pg.getAddress(),
//                pg.getDescription(),
//                pg.getImageUrl(),
//                pg.getUserId()
//        );
//    }
//
//}

import java.util.List;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import com.thryve.pgfinder.dto.request.CreatePgRequest;
import com.thryve.pgfinder.dto.request.CreateRoomRequest;
import com.thryve.pgfinder.dto.request.PgSummaryResponse;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.dto.response.OwnerDto;
import com.thryve.pgfinder.dto.response.PgDetailResponse;
import com.thryve.pgfinder.dto.response.RoomResponse;
import com.thryve.pgfinder.model.Owner;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.Room;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PGMapper{
	
	    PgSummaryResponse toSummary(PG pg);
	  
	    // Pg -> Detail DTO
	    @Mapping(source = "audit.createdAt", target = "createdAt")
	    @Mapping(source = "audit.updatedAt", target = "updatedAt")
	    PgDetailResponse toDetail(PG pg);
	    
	    // Create request -> entity (note: owner / address / other relations should be set in service)
	    PG toEntity(CreatePgRequest request);
	    
	    
	    // Update request -> patch into entity
	    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	    void updatePgFromDto(UpdatePgRequest dto, @MappingTarget PG pg);

	    // Room mappings
	    Room toRoom(CreateRoomRequest request);
	    RoomResponse toRoomResponse(Room room);

	    OwnerDto toOwnerDto(Owner owner);

	    List<RoomResponse> toRoomResponses(List<Room> rooms);

	    List<PgSummaryResponse> toSummaries(List<PG> pgs);

	
}