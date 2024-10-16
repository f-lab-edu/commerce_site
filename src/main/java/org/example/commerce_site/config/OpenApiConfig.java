package org.example.commerce_site.config;

import java.util.ArrayList;
import java.util.List;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Value("${springdoc.server-url}")
	private String serverUrl;

	@Bean
	public OpenAPI openAPI() {
		List<Server> serverList = new ArrayList<>();
		serverList.add(new Server().url(serverUrl));

		Info info = new Info()
			.title("Commerce Site")
			.version("1.0")
			.description("Commerce Site API Documentation");

		Components components = new Components();
		components.addSecuritySchemes("bearerAuth", new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("bearer")
			.bearerFormat("JWT")
			.description("Enter your Bearer token in the format: **Bearer &lt;token&gt;**"));

		return new OpenAPI()
			.components(components)
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
			.info(info)
			.servers(serverList);
	}

	@Bean
	public GroupedOpenApi userOpenApi() {
		String[] paths = {"/users/**"};
		return GroupedOpenApi.builder().group("USER API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi partnerOpenApi() {
		String[] paths = {"/partners/**"};
		return GroupedOpenApi.builder().group("PARTNER API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi productOpenApi() {
		String[] paths = {"/products/**"};
		return GroupedOpenApi.builder().group("PRODUCT API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi categoryOpenApi() {
		String[] paths = {"/categories/**"};
		return GroupedOpenApi.builder().group("CATEGORY API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi addressOpenApi() {
		String[] paths = {"/addresses/**"};
		return GroupedOpenApi.builder().group("ADDRESS API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi cartOpenApi() {
		String[] paths = {"/carts/**"};
		return GroupedOpenApi.builder().group("CART API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi orderOpenApi() {
		String[] paths = {"/orders/**"};
		return GroupedOpenApi.builder().group("ORDER API").pathsToMatch(paths).build();
	}

	@Bean
	public GroupedOpenApi paymentsOpenApi() {
		String[] paths = {"/payments/**"};
		return GroupedOpenApi.builder().group("PAYMENT API").pathsToMatch(paths).build();
	}
}
