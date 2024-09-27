package org.example.commerce_site.representation.user;

import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.config.KeycloakProperties;
import org.example.commerce_site.representation.user.request.UserRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final UserService userService;
	private final KeycloakProperties keycloakProperties;
	@PostMapping("/keycloak/webhook")
	public void createUser(
		@RequestHeader("X-API-KEY") String apiKey,
		@RequestBody UserRequest.Create request) {
		if (!keycloakProperties.getApiKey().equals(apiKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid API Key");
		}
		userService.create(UserRequest.Create.toDTO(request));
	}
}
