package com.thryve.pgfinder.service.Impl;

import com.thryve.pgfinder.config.validation.UtilityValidation;
import com.thryve.pgfinder.dto.request.CreatePgRequest;
import com.thryve.pgfinder.dto.request.CreateRoomRequest;
import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.request.PgSummaryResponse;
import com.thryve.pgfinder.dto.request.UpdatePgRequest;
import com.thryve.pgfinder.dto.response.ImageResponse;
import com.thryve.pgfinder.dto.response.OwnerDto;
import com.thryve.pgfinder.dto.response.PgDetailResponse;
import com.thryve.pgfinder.dto.response.ReviewResponse;
import com.thryve.pgfinder.dto.response.RoomResponse;
import com.thryve.pgfinder.exception.ResourceNotFoundException;
import com.thryve.pgfinder.mapper.PGMapper;
import com.thryve.pgfinder.model.Owner;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.Room;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.AuditModel;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;
import com.thryve.pgfinder.model.common.filter.specification.FiltersSpecification;
import com.thryve.pgfinder.model.common.page.PageRequestDTO;
import com.thryve.pgfinder.model.enums.AmenityType;
import com.thryve.pgfinder.model.enums.AvailabilityStatus;
import com.thryve.pgfinder.model.enums.RoomType;
import com.thryve.pgfinder.repository.OwnerRepository;
import com.thryve.pgfinder.repository.PGRepository;
import com.thryve.pgfinder.repository.RoomRepository;
import com.thryve.pgfinder.service.PGService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.OncePerRequestFilter;

import java.lang.reflect.Field;
import java.time.OffsetDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PGServiceImpl implements PGService {

//	@Autowired
//    private final PGRepository pgRepository;
    
    @Autowired
    private UtilityValidation utilityValidation;
    
    @Autowired
    private FiltersSpecification<PG> pgFiltersSpecification;
    
    private final PGRepository pgRepository;
    private final OwnerRepository ownerRepository;
    private final RoomRepository roomRepository;
    private final PGMapper pgMapper;
    
//    @Override
//    @Transactional
//    public APIResponse createPg(CreatePgRequest request) {
//        APIResponse responseComp = new APIResponse();
//        
//        System.out.println(" name in service impl :"+request.getName());
//
//        // Validation
//        Map<String, Object> fieldsMap = new HashMap<>();
//        for (Field field : CreatePgRequest.class.getDeclaredFields()) {
//            field.setAccessible(true);
//            try {
//                fieldsMap.put(field.getName(), field.get(request));
//            } catch (IllegalAccessException e) {
//                // Handle reflection exception
//                responseComp.setMessage("Failed to access request fields");
//                responseComp.setStatus("error");
//                return responseComp;
//            }
//        }
//
//        Map<String, Object> validationResponse = utilityValidation.validate(fieldsMap);
//        if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
//            String validationMessage = validationResponse.get("message") != null
//                    ? validationResponse.get("message").toString()
//                    : "Validation failed";
//            responseComp.setMessage(validationMessage);
//            responseComp.setStatus("error");
//            return responseComp;
//        }
//
//        // Check for existing PG
////        String name = request.getName().trim().toLowerCase();
////        // TODO: update this to actual address from request
////        String address = "test"; 
////        Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
////        if (existingPg.isPresent()) {
////            response.setMessage("This PG is already listed with the same name and address.");
////            response.setStatus("error");
////            return response;
////        }
//
//        // Resolve owner
//        Owner owner = resolveOwner(request.getOwnerId());
//
//        // Map request to entity
////        PG pg = pgMapper.toEntity(request);
////        System.out.println("after mapping :"+pg.getName());
//        
//        PG pg = new PG();
//        pg.setName(request.getName());
//        pg.setShortDescription(request.getShortDescription());
//        pg.setDescription(request.getDescription());
//        pg.setPgType(request.getPgType());
//        pg.setGenderPreference(request.getGenderPreference());
//        pg.setAddress(request.getAddress());
//        pg.setGeoLocation(request.getGeoLocation());
//        pg.setOwner(owner);
//        pg.setBasePrice(request.getBasePrice());
//        pg.setAmenities(request.getAmenities() != null ? request.getAmenities() : new HashSet<>());
//        pg.setContactEmail(request.getContactEmail());
//        pg.setContactPhone(request.getContactPhone());
//        pg.setTags(request.getTags() != null ? request.getTags() : new HashSet<>());
//
//        // Initialize optional fields with defaults (already handled in entity constructor, but safer here)
//        pg.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
//        pg.setVerified(false);
//        pg.setFeatured(false);
//        pg.setAudit(new AuditModel());
//
//        // Map rooms if provided
//        if (request.getRooms() != null && !request.getRooms().isEmpty()) {
//            for (CreateRoomRequest roomRequest : request.getRooms()) {
//                Room room = new Room();
//                room.setTitle(roomRequest.getTitle());
//                room.setRoomType(roomRequest.getRoomType());
//                room.setCapacity(roomRequest.getCapacity());
//               room.setPrice(roomRequest.getPrice());
//                room.setAvailableUnits(roomRequest.getAvailableUnits());
//                room.setDescription(roomRequest.getDescription());
//                room.setAmenities(roomRequest.getAmenities() != null ? roomRequest.getAmenities() : Set.of());
//
//                room.setPg(pg); // Set the owning side of the relationship
//
//                pg.getRooms().add(room);
//            }
//        }
//
//
//        // Optionally print
//        System.out.println("after mapping: " + pg.getName());
//
//		System.out.println("=== STEP 1: After Mapper ===");
//		System.out.println("Audit object: " + pg.getAudit());
//		if (pg.getAudit() != null) {
//		    System.out.println("Audit.deleted = " + pg.getAudit().isDeleted());
//		} else {
//		    System.out.println("Audit is NULL!");
//		}
//        pg.setOwner(owner);
//
//       
//        if (pg.getAudit() == null) pg.setAudit(new AuditModel());
//       
//        pg.getAudit().setCreatedAt(OffsetDateTime.now());
//        pg.getAudit().setDeleted(false);
//
//        // Save rooms if present
//        if (request.getRooms() != null) {
//            request.getRooms().forEach(cr -> {
//                Room room = pgMapper.toRoom(cr);
//                room.setPg(pg);
//                
//                if (room.getAudit() == null) {
//                    room.setAudit(new AuditModel());
//                }
//                room.getAudit().setCreatedAt(OffsetDateTime.now());
//                room.getAudit().setDeleted(false);
//                
//                System.out.println("=== Room Audit Set ===");
//                System.out.println("Room audit: " + room.getAudit());
//                System.out.println("Room deleted: " + room.getAudit().isDeleted());
//                
//                
//                pg.getRooms().add(room);
//            });
//        }
//
//        System.out.println("=== STEP 2: Before Repository Save ===");
//        System.out.println("Audit object: " + pg.getAudit());
//        if (pg.getAudit() != null) {
//            System.out.println("Audit.deleted = " + pg.getAudit().isDeleted());
//        } else {
//            System.out.println("Audit is NULL!");
//        }
//        // Save PG entity (transactional)
//        PG saved = pgRepository.save(pg);
//        
//        
//        
//        PgDetailResponse response = new PgDetailResponse();
//
//        response.setId(pg.getId());
//        response.setName(pg.getName());
//        response.setShortDescription(pg.getShortDescription());
//        response.setDescription(pg.getDescription());
//        response.setPgType(pg.getPgType());
//        response.setAddress(pg.getAddress());
//        response.setGeoLocation(pg.getGeoLocation());
//        response.setContactPhone(pg.getContactPhone());
//        response.setContactEmail(pg.getContactEmail());
//        response.setAmenities(pg.getAmenities());
//        response.setBasePrice(pg.getBasePrice());
//        response.setAvailabilityStatus(pg.getAvailabilityStatus());
//        response.setVerified(pg.isVerified());
//        response.setFeatured(pg.isFeatured());
//        response.setMaxOccupancy(pg.getMaxOccupancy());
//        response.setTags(pg.getTags() != null ? pg.getTags().stream().toList() : List.of());
//       
//
//              		
//        // ===== Map Rooms to RoomResponse =====
////        List<RoomResponse> roomResponses = pg.getRooms().stream().map(room -> {
////            return RoomResponse.builder()
////                .id(room.getId())
////                .title(room.getTitle())
////                .roomType(room.getRoomType())
////                .capacity(room.getCapacity())
////                .price(room.getMonthlyRent()) // Assuming this is the correct field
////                .availableUnits(room.getAvailableUnits())
////                .description(room.getDescription())
////                .amenities(room.getAmenities())
////               
////                .build();
////        }).toList();
//        response.setRooms(roomResponses);
//
//        // ===== Map Images to ImageResponse =====
//        List<ImageResponse> imageResponses = pg.getImages().stream().map(img -> {
//            return ImageResponse.builder()
//                .id(img.getId())
//                .url(img.getUrl())
//                .caption(img.getCaption())
//                .build();
//        }).toList();
//        response.setImages(imageResponses);
//
//        // ===== Map Reviews to ReviewResponse =====
//        List<ReviewResponse> reviewResponses = pg.getReviews().stream().map(review -> {
//            return ReviewResponse.builder()
//                .id(review.getId())
//                .rating(review.getRating())
//                .comment(review.getComment())
//                .createdAt(review.getCreatedAt())
//                .reviewerName(review.getReviewer().getName()) // assuming you have this
//                .build();
//        }).toList();
//        response.setReviews(reviewResponses);
//
//
//        responseComp.setResult(pgMapper.toDetail(saved));
//        responseComp.setMessage("PG created successfully");
//        responseComp.setStatus("success");
//
//        return responseComp;
//    }

    @Override
    @Transactional
    public APIResponse createPg(CreatePgRequest request) {
        APIResponse responseComp = new APIResponse();

        System.out.println(" name in service impl :" + request.getName());

        // Validation
        Map<String, Object> fieldsMap = new HashMap<>();
        for (Field field : CreatePgRequest.class.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                fieldsMap.put(field.getName(), field.get(request));
            } catch (IllegalAccessException e) {
                responseComp.setMessage("Failed to access request fields");
                responseComp.setStatus("error");
                return responseComp;
            }
        }

        Map<String, Object> validationResponse = utilityValidation.validate(fieldsMap);
        if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
            String validationMessage = validationResponse.get("message") != null
                    ? validationResponse.get("message").toString()
                    : "Validation failed";
            responseComp.setMessage(validationMessage);
            responseComp.setStatus("error");
            return responseComp;
        }

        // Resolve owner
        Owner owner = resolveOwner(request.getOwnerId());

        // Map request to entity manually
        PG pg = new PG();
        pg.setName(request.getName());
        pg.setShortDescription(request.getShortDescription());
        pg.setDescription(request.getDescription());
        pg.setPgType(request.getPgType());
        pg.setGenderPreference(request.getGenderPreference());
        pg.setAddress(request.getAddress());
        pg.setGeoLocation(request.getGeoLocation());
        pg.setOwner(owner);
        pg.setBasePrice(request.getBasePrice());
        pg.setAmenities(request.getAmenities() != null ? request.getAmenities() : new HashSet<>());
        pg.setContactEmail(request.getContactEmail());
        pg.setContactPhone(request.getContactPhone());
        pg.setTags(request.getTags() != null ? request.getTags() : new HashSet<>());

        pg.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        pg.setVerified(false);
        pg.setFeatured(false);

        // Initialize audit if null
        if (pg.getAudit() == null) pg.setAudit(new AuditModel());
        pg.getAudit().setCreatedAt(OffsetDateTime.now());
        pg.getAudit().setDeleted(false);
        
        System.out.println("Owner UserNAme" + owner.getUsername()); 
        pg.setOwner(owner);

        // Map rooms if provided
        if (request.getRooms() != null && !request.getRooms().isEmpty()) {
            for (CreateRoomRequest roomRequest : request.getRooms()) {
                Room room = new Room();
                room.setTitle(roomRequest.getTitle());
                room.setRoomType(roomRequest.getRoomType());
                room.setCapacity(roomRequest.getCapacity());
                room.setPrice(roomRequest.getPrice());
                room.setAvailableUnits(roomRequest.getAvailableUnits());
                room.setDescription(roomRequest.getDescription());
                room.setAmenities(roomRequest.getAmenities() != null ? roomRequest.getAmenities() : Set.of());
                room.setPg(pg);

                if (room.getAudit() == null) room.setAudit(new AuditModel());
                room.getAudit().setCreatedAt(OffsetDateTime.now());
                room.getAudit().setDeleted(false);

                pg.getRooms().add(room);
            }
        }

        System.out.println("after mapping: " + pg.getName());

        // Save PG entity
        PG saved = pgRepository.save(pg);

        // Prepare response manually
        PgDetailResponse response = new PgDetailResponse();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setShortDescription(saved.getShortDescription());
        response.setDescription(saved.getDescription());
        response.setPgType(saved.getPgType());
        response.setAddress(saved.getAddress());
        response.setGeoLocation(saved.getGeoLocation());
        response.setContactPhone(saved.getContactPhone());
        response.setContactEmail(saved.getContactEmail());
        response.setAmenities(saved.getAmenities());
        response.setBasePrice(saved.getBasePrice());
        response.setAvailabilityStatus(saved.getAvailabilityStatus());
        response.setVerified(saved.isVerified());
        response.setFeatured(saved.isFeatured());
        response.setMaxOccupancy(saved.getMaxOccupancy());
        response.setTags(saved.getTags() != null ? saved.getTags().stream().toList() : List.of());

        // Map rooms to RoomResponse manually
        List<RoomResponse> roomResponses = saved.getRooms() == null ? List.of() : saved.getRooms().stream()
                .map(room -> RoomResponse.builder()
                        .id(room.getId())
                        .title(room.getTitle())
                        .roomType(room.getRoomType())
                        .capacity(room.getCapacity())
                        .price(room.getPrice())
                        .availableUnits(room.getAvailableUnits())
                        .description(room.getDescription())
                        .amenities(room.getAmenities())
                        .currentBookings(0) // Set as per your logic
                        .build())
                .toList();
        response.setRooms(roomResponses);

        // Map images to ImageResponse manually
        List<ImageResponse> imageResponses = saved.getImages() == null ? List.of() : saved.getImages().stream()
                .map(img -> ImageResponse.builder()
                        .id(img.getId())
                        .url(img.getUrl())
                        .description(img.getDescription())
                        .sortOrder(img.getSortOrder())
                        .build())
                .toList();
        response.setImages(imageResponses);

        // Map reviews to ReviewResponse manually
//        TODO : Reviews 

        // TODO: Map owner to OwnerDto if needed here
        // response.setOwner(ownerMapper.toDto(saved.getOwner()));

        responseComp.setResult(response);
        responseComp.setMessage("PG created successfully");
        responseComp.setStatus("success");

        return responseComp;
    }

	
	private Owner resolveOwner(String ownerRef) {
        if (ownerRef == null || ownerRef.isBlank()) {
            throw new IllegalArgumentException("ownerId must be provided");
        }

        Optional<Owner> byAuth = ownerRepository.findByAuthUserId(ownerRef);
        if (byAuth.isPresent()) return byAuth.get();

        try {
            UUID id = UUID.fromString(ownerRef);
            return ownerRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Owner :"+ ownerRef));
        } catch (IllegalArgumentException ex) {
            Owner o = Owner.builder()
            		 .authUserId(ownerRef)
                    .fullName("Unknown Owner")
                    .build();
            return ownerRepository.save(o);
        }
    }

//	Update Pg
	@Override
	public APIResponse updatePg(UUID pgId, UpdatePgRequest request) {
		APIResponse response = new APIResponse();
		Optional<PG> optionalPg = pgRepository.findByIdAndAuditDeletedFalse(pgId);
        if (optionalPg.isPresent()) {
            PG pg = optionalPg.get();
            response.setStatus("success");
            response.setMessage("PG retrieved successfully");
            response.setResult(pgMapper.toDetail(pg)); 
        } else {
            response.setStatus("error");
            response.setMessage("PG not found with ID: " + pgId);
        }
        
        pgMapper.updatePgFromDto(request, optionalPg.get());
        
        if (optionalPg.get().getAudit() == null) optionalPg.get().setAudit(new AuditModel());
        optionalPg.get().getAudit().setUpdatedAt(OffsetDateTime.now());
        
        PG updated = pgRepository.save(optionalPg.get());
        
        response.setResult(pgMapper.toDetail(updated));
        response.setMessage("PG created successfully");
        response.setStatus("success");
        
		return response;
	}

//	Get Pg by Id
	@Override
	@Transactional(readOnly = true)
	public APIResponse getPgById(UUID pgId) {
		APIResponse response = new APIResponse();
		try {
			Optional<PG> optionalPg = pgRepository.findByIdAndAuditDeletedFalse(pgId);
	        if (optionalPg.isPresent()) {
	            PG pg = optionalPg.get();
	            response.setStatus("success");
	            response.setMessage("PG retrieved successfully");
	            response.setResult(pgMapper.toDetail(pg)); 
	        } else {
	            response.setStatus("error");
	            response.setMessage("PG not found with ID: " + pgId);
	        }
		} catch (Exception e) {
			response.setStatus("error");
	        response.setMessage("An unexpected error occurred while fetching the PG.");
	        e.printStackTrace();
		}
		
		return response;
	}

//	Get All Pg
	@Override
	@Transactional(readOnly = true)
	public APIResponse listPgs(FetchAPIRequest fetchAPIRequest) {
		 APIResponse response = new APIResponse();
		 
		 try {
		    Specification<PG> searchSpecification = this.pgFiltersSpecification.getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
            Pageable pageable = (new PageRequestDTO()).getPageable(fetchAPIRequest.getPageRequestDTO());
            Page<PG> page = this.pgRepository.findAll(searchSpecification, pageable);
            
            Page<PgSummaryResponse> summaryPage = page.map(this::toSummary);
            
            response.setResult(summaryPage);
            
            
            response.setMessage("pg's Details Fetched Successfully.");
            response.setStatus("success");
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
		return response;

	}

	public PgSummaryResponse toSummary(PG pg) {
	    if (pg == null) {
	        return null;
	    }

	    // Get the first image URL if exists
	    String thumbnailImageUrl = null;
	    if (pg.getImages() != null && !pg.getImages().isEmpty()) {
	        thumbnailImageUrl = pg.getImages().get(0).getUrl(); // Assuming PgImage has getUrl()
	    }

	    // Calculate average rating and review count
	    double avgRating = 0.0;
	    int reviewCount = 0;
	    if (pg.getReviews() != null && !pg.getReviews().isEmpty()) {
	        reviewCount = pg.getReviews().size();
	        double totalRating = pg.getReviews().stream()
	                                .mapToDouble(r -> r.getRating() != null ? r.getRating() : 0)
	                                .sum();
	        avgRating = totalRating / reviewCount;
	    }

	    return PgSummaryResponse.builder()
	            .id(pg.getId())
	            .name(pg.getName())
	            .shortDescription(pg.getShortDescription())
	            .address(pg.getAddress())
	            .geoLocation(pg.getGeoLocation())
	            .basePrice(pg.getBasePrice())
	            .availabilityStatus(pg.getAvailabilityStatus())
	            .pgType(pg.getPgType())
	            .amenities(pg.getAmenities())
	            .thumbnailImageUrl(thumbnailImageUrl)
	            .avgRating(avgRating > 0 ? avgRating : null)
	            .reviewCount(reviewCount)
	            .verified(pg.isVerified())
	            .build();
	}

	
	
//	delete Pg
	@Override
	@Transactional
	public APIResponse deletePg(UUID pgId) {
		APIResponse response = new APIResponse();
		PG pg = new PG();
		try {
			Optional<PG> optionalPg = pgRepository.findById(pgId);
	        if (optionalPg.isPresent()) {
	            pg = optionalPg.get();
	           
	        } else {
	            response.setStatus("error");
	            response.setMessage("PG not found with ID: " + pgId);
	        }
	        if (pg.getAudit() == null) pg.setAudit(new AuditModel());
	        pg.getAudit().setDeleted(true);
	        pg.getAudit().setDeletedAt(OffsetDateTime.now());
	        pgRepository.save(pg);
	        response.setStatus("success");
	        response.setMessage("Pg deleted successfully with id :"+pgId);
		} catch (Exception e) {
			e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
		
		return response;
		
	}

//	Add room to a pg 
	@Override
	@Transactional
	public APIResponse addRoom(UUID pgId, CreateRoomRequest req) {
		APIResponse response = new APIResponse();
		PG pg = new PG();
		try {
			Optional<PG> optionalPg = pgRepository.findByIdAndAuditDeletedFalse(pgId);
	        if (optionalPg.isPresent()) {
	            pg = optionalPg.get();
	           
	        } else {
	            response.setStatus("error");
	            response.setMessage("PG not found with ID: " + pgId);
	        }
        Room room = pgMapper.toRoom(req);
        room.setPg(pg);
        
        Room saved = roomRepository.save(room);
        
        pg.getRooms().add(saved);
        pgRepository.save(pg);
        
        response.setStatus("success");
        response.setMessage("Room saved successflly");
        response.setResult(pgMapper.toRoomResponse(saved));
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
		return response;
	}

//	 update Room of a pg
	@Override
	public APIResponse updateRoom(UUID roomId, CreateRoomRequest req) {
		APIResponse response = new APIResponse();
		Room room = new Room();
		try {
			Optional<Room> optionalRoom = roomRepository.findById(roomId);
	        if (optionalRoom.isPresent()) {
	            room = optionalRoom.get();
	           
	        } else {
	            response.setStatus("error");
	            response.setMessage("room not found with ID: " + roomId);
	        }
	        if (req.getTitle() != null) room.setTitle(req.getTitle());
	        if (req.getRoomType() != null) room.setRoomType(req.getRoomType());
	        if (req.getCapacity() != null) room.setCapacity(req.getCapacity());
	        if (req.getPrice() != null) room.setPrice(req.getPrice());
//	        if (req.getPriceFrequency() != null) room.setPriceFrequency(req.getPriceFrequency());
	        if (req.getAvailableUnits() != null) room.setAvailableUnits(req.getAvailableUnits());
	        if (req.getDescription() != null) room.setDescription(req.getDescription());
	        if (req.getAmenities() != null) {
	            room.setAmenities(req.getAmenities());
	        }
	        Room saved = roomRepository.save(room);
	        response.setStatus("success");
	        response.setMessage("room updated successfully");
	        response.setResult(pgMapper.toRoomResponse(saved));
	    }
        catch (Exception e) {
        	e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
		return response;
	}

//	delete room of a pg
	@Override
	public APIResponse deleteRoom(UUID roomId) {
		APIResponse response = new APIResponse();
		Room room = new Room();
		try {
			Optional<Room> optionalRoom = roomRepository.findById(roomId);
	        if (optionalRoom.isPresent()) {
	            room = optionalRoom.get();
	           
	        } else {
	            response.setStatus("error");
	            response.setMessage("room not found with ID: " + roomId);
	        }
	      
	        roomRepository.delete(room);
	        response.setStatus("success");
	        response.setMessage("room deleted successfully with id:"+roomId);
	      
	    }
        catch (Exception e) {
        	e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
	return response;
	
	}

//
//
//
//    @Override
//    public APIResponse getPGsByUser(String userId, FetchAPIRequest fetchAPIRequest) {
//        APIResponse response = new APIResponse();
//
//        try {
//            Specification<PG> searchSpecification = this.pgFiltersSpecification
//                    .getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
//
//            Specification<PG> userIdSpec = (root, query, criteriaBuilder) ->
//                    criteriaBuilder.equal(root.get("userId"), userId);
//
//            Specification<PG> finalSpec = (searchSpecification == null)
//                    ? userIdSpec
//                    : searchSpecification.and(userIdSpec);
//
//            Pageable pageable = new PageRequestDTO().getPageable(fetchAPIRequest.getPageRequestDTO());
//
//            Page<PG> pgPage = pgRepository.findAll(finalSpec, pageable);
//
//            response.setResult(pgPage);
//            response.setMessage("PGs fetched successfully.");
//            response.setStatus("success");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.setMessage("Error: " + e.getMessage());
//            response.setStatus("error");
//        }
//
//        return response;
//    }
//
//
//
//
//    @Override
//	public APIResponse createPG(PGRequest pgRequest) {
//		APIResponse response = new APIResponse();
//    	
//    	try {

//            Map<String, Object> fieldsMap = new HashMap<>();
//            for (Field field : PGRequest.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                fieldsMap.put(field.getName(), field.get(pgRequest));
//            }
//
//            Map<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
//            if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
//                String validationMessage = validationResponse.get("message") != null
//                        ? validationResponse.get("message").toString()
//                        : "Validation failed";
//                response.setMessage(validationMessage);
//                response.setStatus("error");
//                return response;
//            }
//            String name = pgRequest.getName().trim().toLowerCase();
//            String address = pgRequest.getAddress().trim().toLowerCase();
//
//            Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
//            if (existingPg.isPresent()) {
//                response.setMessage("This PG is already listed with the same name and address.");
//                response.setStatus("error");
//                return response;
//            }
//              PG pg = new PG();
////              pg.setId(pgRequest.getUserId());
//              pg.setName(pgRequest.getName());
//              pg.setAddress(pgRequest.getAddress());
//              pg.setDescription(pgRequest.getDescription());
//              pg.setImageUrl(pgRequest.getImageUrl());
//              pg.setUserId(pgRequest.getUserId());
//
//            PG savedPg = this.pgRepository.save(pg);
//              response.setResult(savedPg);
//              response.setMessage("Pg created Succesfully");
//              response.setStatus("success");;
//			
//		} catch (Exception e) {
//			response.setMessage(e.getMessage());
//            response.setStatus("error");
//		}
//    	
//    	
////        String name = dto.getName().trim().toLowerCase();
////        String address = dto.getAddress().trim().toLowerCase();
////
////        Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
////        if (existingPg.isPresent()) {
////            throw new PGAlreadyExistException("This PG is already listed with the same name and address.");
////        }
////
////        // Map DTO to entity and save
////        PG pg = PGMapper.toEntity(dto);
////        PG savedPG = pgRepository.save(pg);
////        return PGMapper.toDto(savedPG);
//    	return response;
//    
//	}
//
////    @Override
////    public PGResponse updatePG(String pgId, PGRequest dto) {
////        return null;
////    }
//
//    @Override
//    public APIResponse updatePG(String pgId, PGRequest pgRequest){
//        APIResponse response = new APIResponse();
//        try {
////            HashMap<String, Object> fieldsMap = new HashMap();
////            Class<?> clazz = PGRequest.class;
////            List<String> bypassList = Arrays.asList();
////
////            for(Field field : clazz.getDeclaredFields()) {
////                field.setAccessible(true);
////                if (!bypassList.contains(field.getName())) {
////                    fieldsMap.put(field.getName(), field.get(pgRequest));
////                }
////            }
////
////            HashMap<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
////            if ((Boolean)validationResponse.get("status")) {
////                response.setMessage(validationResponse.get("message").toString());
////                response.setStatus("error");
////                return response;
////            }
//            Map<String, Object> fieldsMap = new HashMap<>();
//            for (Field field : PGRequest.class.getDeclaredFields()) {
//                field.setAccessible(true);
//                fieldsMap.put(field.getName(), field.get(pgRequest));
//            }
//
//            Map<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
//            if (!(Boolean.TRUE.equals(validationResponse.get("status")))) {
//                String validationMessage = validationResponse.get("message") != null
//                        ? validationResponse.get("message").toString()
//                        : "Validation failed";
//                response.setMessage(validationMessage);
//                response.setStatus("error");
//                return response;
//            }
//            PG existingPg = pgRepository.findById(pgId)
//                    .orElseThrow(() -> new ResourceNotFoundException("PG not found with id: " + pgId));
//
//            String name = pgRequest.getName().trim().toLowerCase();
//            String address = pgRequest.getAddress().trim().toLowerCase();
//
//            Optional<PG> duplicatePg  = pgRepository.findByNameAndAddress(name, address);
//            if (duplicatePg .isPresent()) {
//                response.setMessage("This PG is already listed with the same name and address.");
//                response.setStatus("error");
//                return response;
//            }
//
//            //  pg.setId(pgRequest.getUserId());
//            existingPg.setName(pgRequest.getName());
//            existingPg.setAddress(pgRequest.getAddress());
//            existingPg.setDescription(pgRequest.getDescription());
//            existingPg.setImageUrl(pgRequest.getImageUrl());
//            existingPg.setUserId(pgRequest.getUserId());
//
//            PG savedPg = this.pgRepository.save(existingPg);
//            response.setResult(savedPg);
//            response.setMessage("Pg updated Succesfully");
//            response.setStatus("success");;
//
//        } catch (Exception e) {
//            response.setMessage(e.getMessage());
//            response.setStatus("error");
//        }
//
//
//        return response;
//    }
//
//
//
//
//    @Override
//	public APIResponse fetchAll(FetchAPIRequest fetchAPIRequest) {
//		 APIResponse response = new APIResponse();
//		 
//		 try {
//			   Specification<PG> searchSpecification = this.pgFiltersSpecification.getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
//	            Pageable pageable = (new PageRequestDTO()).getPageable(fetchAPIRequest.getPageRequestDTO());
//	            Page<PG> cameraDetailsPage = this.pgRepository.findAll(searchSpecification, pageable);
//	            response.setResult(cameraDetailsPage);
//	            response.setMessage("pg's Details Fetched Successfully.");
//	            response.setStatus("success");
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//            response.setMessage(e.getMessage());
//            response.setStatus("error");
//		}
//		 
//		 
//		 return response;
//	}
//    @Override
//	public DeleteRequest deletePG(String pgId){
//      pgRepository.deleteById(pgId);
//      return new DeleteRequest(pgId);
//
//    }
}
