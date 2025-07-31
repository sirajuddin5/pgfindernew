package com.thryve.pgfinder.config.validation;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;

@Service
public class UtilityValidation {
	
	 public HashMap<String, Object> validate(HashMap<String, Object> fieldsMap) {
	        HashMap<String, Object> response = new HashMap();

	        try {
	            response.put("status", false);

	            for(String key : fieldsMap.keySet()) {
	                Object value = fieldsMap.get(key);
	                if (value == null) {
	                    response.put("status", true);
	                    response.put("message", key + " cannot be null");
	                    break;
	                }

	                if (value instanceof String && ((String)value).trim().isEmpty()) {
	                    response.put("status", true);
	                    response.put("message", key + " cannot be empty");
	                    break;
	                }

	                if (value instanceof Integer && (Integer)value == 0) {
	                    response.put("status", true);
	                    response.put("message", key + " cannot be zero");
	                    break;
	                }

	                if (value instanceof List && ((List)value).isEmpty()) {
	                    response.put("status", true);
	                    response.put("message", key + " cannot be empty list");
	                    break;
	                }

	                if (!(value instanceof String) && !(value instanceof Integer) && !(value instanceof List) && value == null) {
	                    response.put("status", true);
	                    response.put("message", key + " cannot be null object");
	                    break;
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return response;
	    }

}
