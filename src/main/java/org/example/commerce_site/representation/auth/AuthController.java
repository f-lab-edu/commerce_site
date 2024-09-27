package org.example.commerce_site.representation.auth;

import org.example.commerce_site.application.auth.KeycloakAuthService;
import org.example.commerce_site.application.auth.dto.OAuthAccessTokenResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
	private final KeycloakAuthService keycloakAuthService;

	@GetMapping("/callback")
	public OAuthAccessTokenResponse.Keycloak auth(@RequestParam String code) {
		return keycloakAuthService.getAccessToken(code);
	}
}
