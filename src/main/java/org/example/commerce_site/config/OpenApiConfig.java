package org.example.commerce_site.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Controller;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	private static final String BASE_PACKAGE = "org.example.commerce_site.representation";

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
	public List<GroupedOpenApi> groupedOpenApisByPackage() {
		List<GroupedOpenApi> groupedApis = new ArrayList<>();

		Set<String> subPackages = discoverControllerGroups(BASE_PACKAGE);

		for (String pkg : subPackages) {
			String groupName = pkg.substring(pkg.lastIndexOf('.') + 1).toUpperCase() + " API";

			groupedApis.add(GroupedOpenApi.builder()
				.group(groupName)
				.packagesToScan(pkg)
				.build());
		}

		return groupedApis;
	}

	private Set<String> discoverControllerGroups(String basePackage) {
		ClassPathScanningCandidateComponentProvider scanner =
			new ClassPathScanningCandidateComponentProvider(false);

		scanner.addIncludeFilter(new AnnotationTypeFilter(Controller.class));

		return scanner.findCandidateComponents(basePackage).stream()
			.map(beanDefinition -> {
				String className = beanDefinition.getBeanClassName();
				return className.substring(0, className.lastIndexOf('.'));
			})
			.collect(Collectors.toSet());
	}
}
