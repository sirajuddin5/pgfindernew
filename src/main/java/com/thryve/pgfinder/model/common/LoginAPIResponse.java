package com.thryve.pgfinder.model.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAPIResponse {
	
	private String status;
    private String token;
    private String message;
    private Object result;

}
