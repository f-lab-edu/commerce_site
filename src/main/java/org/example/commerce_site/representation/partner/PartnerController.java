package org.example.commerce_site.representation.partner;

import org.example.commerce_site.application.auth.KeycloakAuthService;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.config.KeycloakProperties;
import org.example.commerce_site.representation.partner.dto.PartnerRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/partners")
public class PartnerController {
	private final PartnerService partnerService;
	private final KeycloakAuthService keycloakAuthService;
	private final KeycloakProperties keycloakProperties;

	@PostMapping()
	public ApiSuccessResponse createPartner(
		@Valid @RequestBody PartnerRequest.Create request) {
		keycloakAuthService.createPartner(PartnerRequest.Create.toDTO(request));
		return ApiSuccessResponse.success();
	}

	@PostMapping("/keycloak/webhook")
	public void createPartnerWebhook(
		@RequestHeader("X-API-KEY") String apiKey,
		@RequestBody PartnerRequest.CreateWebHook request) {
		if (!keycloakProperties.getApiKey().equals(apiKey)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid API Key");
		}
		partnerService.create(PartnerRequest.CreateWebHook.toDTO(request));
	}
}
