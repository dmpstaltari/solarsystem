package com.ml.solarsystem.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ml.solarsystem.controllers"))
				.paths(PathSelectors.regex("/solarsystem.*"))
				.build()
				.apiInfo(getApiInfo());
	}

	private ApiInfo getApiInfo() {
		return new ApiInfo(
				"Solar system WP REST API",
				"Solar System API's for weather prediction",
				"1.0.0",
				"https://smartbear.com/terms-of-use/",
				new Contact("Dario Staltari","http://github.com/dmpstaltari","dmpstaltari@gmail.com"),
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0.html",
				Collections.emptyList()
				);
	}
}
