package com.thryve.pgfinder.service.Impl;

import com.thryve.pgfinder.config.validation.UtilityValidation;
import com.thryve.pgfinder.dto.request.PGRequest;
import com.thryve.pgfinder.dto.response.PGResponse;
import com.thryve.pgfinder.exception.AlreadyExistException;
import com.thryve.pgfinder.mapper.PGMapper;
import com.thryve.pgfinder.model.PG;
import com.thryve.pgfinder.model.common.APIResponse;
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
import java.util.stream.Collectors;

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

    @Override
    public PGResponse updatePG(PGRequest dto){
        String name = dto.getName().trim().toLowerCase();
        String address = dto.getAddress().trim().toLowerCase();
        Optional <PG> existingPg = pgRepository.findByNameAndAddress(name, address);
        if(existingPg.isPresent()){
            throw  new AlreadyExistException("This PG is already listed with the same name and address.");
        }
        PG pg = PGMapper.toEntity(dto);
        PG savedPg = pgRepository.save(pg);
        return  PGMapper.toDto(savedPg);
    }

    @Override
    public List<PGResponse> getPGsByUser(String userId) {
        return pgRepository.findByUserId(userId)
                .stream()
                .map(PGMapper::toDto)
                .collect(Collectors.toList());
    }
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
              
              PG pg = new PG();
            //  pg.setId(pgRequest.getUserId());
              pg.setName(pgRequest.getName());
              pg.setAddress(pgRequest.getAddress());
              pg.setDescription(pgRequest.getDescription());
              pg.setImageUrl(pgRequest.getImageUrl());
              
              PG savedPg = this.pgRepository.save(pg);
              response.setResult(savedPg);
              response.setMessage("Pg saved Succesfully");
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
	
}
