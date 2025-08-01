package com.thryve.pgfinder.service.Impl;

import com.thryve.pgfinder.config.validation.UtilityValidation;
import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.exception.ResourceNotFoundException;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.common.APIResponse;
import com.thryve.pgfinder.model.common.DeleteRequest;
import com.thryve.pgfinder.model.common.FetchAPIRequest;
import com.thryve.pgfinder.model.common.filter.specification.FiltersSpecification;
import com.thryve.pgfinder.model.common.page.PageRequestDTO;
import com.thryve.pgfinder.repository.PGRepository;
import com.thryve.pgfinder.service.PGService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PGServiceImpl implements PGService {

	@Autowired
    private final PGRepository pgRepository;
    
    @Autowired
    private UtilityValidation utilityValidation;
    
    @Autowired
    private FiltersSpecification<PG> pgFiltersSpecification;


//     @Override
//     public PGResponse createPG(PGRequest dto) {
//         // Trim and normalize input to avoid duplicate entries with casing or spaces
//         String name = dto.getName().trim().toLowerCase();
//         String address = dto.getAddress().trim().toLowerCase();

//         Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
//         if (existingPg.isPresent()) {
//             throw new AlreadyExistException("This PG is already listed with the same name and address.");
//         }

//         // Map DTO to entity and save
//         PG pg = PGMapper.toEntity(dto);
//         PG savedPG = pgRepository.save(pg);
//         return PGMapper.toDto(savedPG);
//     }
//_______
//    @Override/
//    public PGResponse updatePG(String pgId, PGRequest dto) {
//        // 1. Fetch the existing PG by ID
//        PG existingPg = pgRepository.findById(pgId)
//                .orElseThrow(() -> new ResourceNotFoundException("PG not found with id: " + pgId));
//
//        // 2. Normalize name & address
//        String name = dto.getName().trim().toLowerCase();
//        String address = dto.getAddress().trim().toLowerCase();
//
//        // 3. Check for duplicate PG with same name/address but different ID
//        Optional<PG> duplicatePg = pgRepository.findByNameAndAddress(name, address);
//        if (duplicatePg.isPresent() && !duplicatePg.get().getId().equals(pgId)) {
//            throw new AlreadyExistException("This PG is already listed with the same name and address.");
//        }
//
//        // 4. Update fields
//        existingPg.setName(dto.getName());
//        existingPg.setAddress(dto.getAddress());
//        existingPg.setDescription(dto.getDescription());
//        existingPg.setImageUrl(dto.getImageUrl());
//        existingPg.setUserId(dto.getUserId());
//
//        // 5. Save and return response
//        PG updatedPg = pgRepository.save(existingPg);
//        return PGMapper.toDto(updatedPg);
//    }


    @Override
    public APIResponse getPGsByUser(String userId, FetchAPIRequest fetchAPIRequest) {
        APIResponse response = new APIResponse();

        try {
            Specification<PG> searchSpecification = this.pgFiltersSpecification
                    .getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());

            Specification<PG> userIdSpec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("userId"), userId);

            Specification<PG> finalSpec = (searchSpecification == null)
                    ? userIdSpec
                    : searchSpecification.and(userIdSpec);

            Pageable pageable = new PageRequestDTO().getPageable(fetchAPIRequest.getPageRequestDTO());

            Page<PG> pgPage = pgRepository.findAll(finalSpec, pageable);

            response.setResult(pgPage);
            response.setMessage("PGs fetched successfully.");
            response.setStatus("success");

        } catch (Exception e) {
            e.printStackTrace();
            response.setMessage("Error: " + e.getMessage());
            response.setStatus("error");
        }

        return response;
    }



//    @Override
//    public PGResponse updatePG(PGRequest dto) {
//        return null;
//    }

    @Override
	public APIResponse createPG(PGRequest pgRequest) {
		APIResponse response = new APIResponse();
    	
    	try {
    		 HashMap<String, Object> fieldsMap = new HashMap();
    		  Class<?> clazz = PGRequest.class;
    		  List<String> bypassList = Arrays.asList();
    		  
    		  for(Field field : clazz.getDeclaredFields()) {
                  field.setAccessible(true);
                  if (!bypassList.contains(field.getName())) {
                      fieldsMap.put(field.getName(), field.get(pgRequest));
                  }
              }
    		  
    		  HashMap<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
              if ((Boolean)validationResponse.get("status")) {
                  response.setMessage(validationResponse.get("message").toString());
                  response.setStatus("error");
                  return response;
              }
            String name = pgRequest.getName().trim().toLowerCase();
            String address = pgRequest.getAddress().trim().toLowerCase();

            Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
            if (existingPg.isPresent()) {
                response.setMessage("This PG is already listed with the same name and address.");
                response.setStatus("error");
                return response;
            }
              PG pg = new PG();
//              pg.setId(pgRequest.getUserId());
              pg.setName(pgRequest.getName());
              pg.setAddress(pgRequest.getAddress());
              pg.setDescription(pgRequest.getDescription());
              pg.setImageUrl(pgRequest.getImageUrl());
              pg.setUserId(pgRequest.getUserId());

            PG savedPg = this.pgRepository.save(pg);
              response.setResult(savedPg);
              response.setMessage("Pg created Succesfully");
              response.setStatus("success");;
			
		} catch (Exception e) {
			response.setMessage(e.getMessage());
            response.setStatus("error");
		}
    	
    	
//        String name = dto.getName().trim().toLowerCase();
//        String address = dto.getAddress().trim().toLowerCase();
//
//        Optional<PG> existingPg = pgRepository.findByNameAndAddress(name, address);
//        if (existingPg.isPresent()) {
//            throw new PGAlreadyExistException("This PG is already listed with the same name and address.");
//        }
//
//        // Map DTO to entity and save
//        PG pg = PGMapper.toEntity(dto);
//        PG savedPG = pgRepository.save(pg);
//        return PGMapper.toDto(savedPG);
    	return response;
    
	}

//    @Override
//    public PGResponse updatePG(String pgId, PGRequest dto) {
//        return null;
//    }

    @Override
    public APIResponse updatePG(String pgId, PGRequest pgRequest){
        APIResponse response = new APIResponse();
        try {
            HashMap<String, Object> fieldsMap = new HashMap();
            Class<?> clazz = PGRequest.class;
            List<String> bypassList = Arrays.asList();

            for(Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (!bypassList.contains(field.getName())) {
                    fieldsMap.put(field.getName(), field.get(pgRequest));
                }
            }

            HashMap<String, Object> validationResponse = this.utilityValidation.validate(fieldsMap);
            if ((Boolean)validationResponse.get("status")) {
                response.setMessage(validationResponse.get("message").toString());
                response.setStatus("error");
                return response;
            }
            PG existingPg = pgRepository.findById(pgId)
                    .orElseThrow(() -> new ResourceNotFoundException("PG not found with id: " + pgId));

            String name = pgRequest.getName().trim().toLowerCase();
            String address = pgRequest.getAddress().trim().toLowerCase();

            Optional<PG> duplicatePg  = pgRepository.findByNameAndAddress(name, address);
            if (duplicatePg .isPresent()) {
                response.setMessage("This PG is already listed with the same name and address.");
                response.setStatus("error");
                return response;
            }

            //  pg.setId(pgRequest.getUserId());
            existingPg.setName(pgRequest.getName());
            existingPg.setAddress(pgRequest.getAddress());
            existingPg.setDescription(pgRequest.getDescription());
            existingPg.setImageUrl(pgRequest.getImageUrl());
            existingPg.setUserId(pgRequest.getUserId());

            PG savedPg = this.pgRepository.save(existingPg);
            response.setResult(savedPg);
            response.setMessage("Pg updated Succesfully");
            response.setStatus("success");;

        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setStatus("error");
        }


        return response;
    }




    @Override
	public APIResponse fetchAll(FetchAPIRequest fetchAPIRequest) {
		 APIResponse response = new APIResponse();
		 
		 try {
			   Specification<PG> searchSpecification = this.pgFiltersSpecification.getSearchSpecification(fetchAPIRequest.getFilterList(), fetchAPIRequest.getGlobalOperator());
	            Pageable pageable = (new PageRequestDTO()).getPageable(fetchAPIRequest.getPageRequestDTO());
	            Page<PG> cameraDetailsPage = this.pgRepository.findAll(searchSpecification, pageable);
	            response.setResult(cameraDetailsPage);
	            response.setMessage("pg's Details Fetched Successfully.");
	            response.setStatus("success");
			
		} catch (Exception e) {
			e.printStackTrace();
            response.setMessage(e.getMessage());
            response.setStatus("error");
		}
		 
		 
		 return response;
	}
    @Override
	public DeleteRequest deletePG(String pgId){
      pgRepository.deleteById(pgId);
      return new DeleteRequest(pgId);

    }
}
