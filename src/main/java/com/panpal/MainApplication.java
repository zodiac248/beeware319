package com.panpal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
	 	return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedHeaders("Access-Control-Allow-Headers", "Access-Control-Allow-Origin","Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin"
								,"Cache-Control", "Content-Type")
						.allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")
						.allowedOrigins("https://beeware319-front.herokuapp.com")
						.allowCredentials(true);
			}
		};
	 }

}
