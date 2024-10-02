package org.example.commerce_site.config;

import org.example.commerce_site.config.filter.UserIdFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final String[] AUTH_EXCLUDE_POST_API_LIST = {"/users/keycloak/webhook"};
	private static final String[] AUTH_EXCLUDE_GET_API_LIST = {"/auth/**"};
	private static final String[] AUTH_EXCLUDE_WEB_LIST = {"/swagger-ui/**", "/api-docs/**"};

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(HttpMethod.POST, AUTH_EXCLUDE_POST_API_LIST).permitAll()
				.requestMatchers(HttpMethod.GET, AUTH_EXCLUDE_GET_API_LIST).permitAll()
				.requestMatchers(AUTH_EXCLUDE_WEB_LIST).permitAll()
				.anyRequest().authenticated()
			)
			.oauth2ResourceServer(
				oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		http.addFilterAfter(new UserIdFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
