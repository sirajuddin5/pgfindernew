package com.thryve.pgfinder.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI api() {
        return new OpenAPI()
            .info(new Info().title("PG Finder Application")
					.description("This application is developed for PG search and upload the vacancy by user for respective Pgs")
					.version("1.0.0")
					.contact(new Contact()
							.name("Sirajuddin & Ashish Ranjan")
							.email("sirajuddin53@gmail.com, arics5490@gmail.com")
							.url("https://www.linkedin.com/in/aranjan90/")

					)
					.summary("A user can use this to find, create, update PG lists using this")
)
            .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
            .components(new Components().addSecuritySchemes("bearerAuth",
                new SecurityScheme()
                    .name("bearerAuth")
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
            ));
    }
}
