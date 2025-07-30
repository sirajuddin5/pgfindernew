package com.thryve.pgfinder;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class PgFinderApplication {
	public static void main(String[] args) {
		SpringApplication.run(PgFinderApplication.class, args);
	}
	@Bean
	public OpenAPI customOpenAPI(){
		return new OpenAPI()
				.info(new Info()
						.title("PG Finder Application")
						.description("This application is developed for PG search and upload the vacancy by user for respective Pgs")
						.version("1.0.0")
						.contact(new Contact()
								.name("Sirajuddin & Ashish Ranjan")
								.email("sirajuddin53@gmail.com, arics5490@gmail.com")
								.url("https://www.linkedin.com/in/aranjan90/")

						)
						.summary("A user can use this to find, create, update PG lists using this")
				);
	}
}
