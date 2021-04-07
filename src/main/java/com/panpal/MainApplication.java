package com.panpal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class MainApplication {

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}

// 	@Bean
//         public WebMvcConfigurer corsConfigurer() {
//             return new WebMvcConfigurerAdapter() {
//                 @Override
//                 public void addCorsMappings(CorsRegistry registry) {
//                     registry.addMapping("/**").allowedOrigins("http://localhost:3000");
//                 }
//             };
//         }

	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
	 	return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedHeaders("*")
						.allowedMethods("*")
						.allowedOrigins("https://beeware319-front.herokuapp.com")
						.allowCredentials(true);
			}
	 	};
	 }
	@Bean
	public HttpFirewall defaultHttpFirewall() {
		return new DefaultHttpFirewall();
	}

}
