package com.thryve.pgfinder.config.validation;

import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UtilityValidation {

	public HashMap<String, Object> validate(Map<String, Object> fieldsMap) {
		HashMap<String, Object> response = new HashMap<>();
		response.put("status", true);

		try {
			for (String key : fieldsMap.keySet()) {
				Object value = fieldsMap.get(key);

				if (value == null) {
					response.put("status", false);
					response.put("message", key + " cannot be null");
					break;
				}

				if (value instanceof String && ((String) value).trim().isEmpty()) {
					response.put("status", false);
					response.put("message", key + " cannot be empty");
					break;
				}

				if (value instanceof Integer && (Integer) value == 0) {
					response.put("status", false);
					response.put("message", key + " cannot be zero");
					break;
				}

				if (value instanceof List && ((List<?>) value).isEmpty()) {
					response.put("status", false);
					response.put("message", key + " cannot be empty list");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("status", false);
			response.put("message", "Validation error: " + e.getMessage());
		}

		return response;
	}
}
