package org.example.commerce_site.config;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;

import io.swagger.v3.core.jackson.ModelResolver;

@Configuration
class ModelResolverConfig {
	@Bean
	public ModelResolver modelResolver(ObjectMapper objectMapper) {
		return new ModelResolver(objectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE));
	}
}