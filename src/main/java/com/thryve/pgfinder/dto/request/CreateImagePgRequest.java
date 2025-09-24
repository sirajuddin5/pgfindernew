package com.thryve.pgfinder.dto.request;



import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateImagePgRequest {
	
	    @NotNull
	    private String pgId;

	    private String description;
	    
	    @Size(max= 256)
	    @NotNull
	    private String imageUrl;

}
