package org.example.commerce_site.config;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.commerce_site.config.filter.UserIdFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
	private final AuthExcludeProperties authExcludeProperties;

	public SecurityConfig(AuthExcludeProperties authExcludeProperties) {
		this.authExcludeProperties = authExcludeProperties;
	}

	@Bean
	protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
		return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(this::extractAuthorities);

		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.authorizeHttpRequests(requests -> requests
				.requestMatchers(HttpMethod.POST, authExcludeProperties.getPost()).permitAll()
				.requestMatchers(HttpMethod.GET, authExcludeProperties.getGet()).permitAll()
				.requestMatchers(authExcludeProperties.getWeb()).permitAll()
				.anyRequest().authenticated()
			)
			.addFilterAfter(new UserIdFilter(authExcludeProperties), UsernamePasswordAuthenticationFilter.class)
			.oauth2ResourceServer(
				oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)))
			.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

		return http.build();
	}

	private Collection<GrantedAuthority> extractAuthorities(Jwt jwt) {
		var resourceAccess = (Map<String, Object>)jwt.getClaim("resource_access");
		var roles = (Map<String, Object>)resourceAccess.get("oauth2-client-app");

		if (roles != null) {
			log.info(roles.toString());

			var roleList = (List<String>)roles.get("roles");
			return roleList.stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList());
		}

		return List.of();
	}
}
