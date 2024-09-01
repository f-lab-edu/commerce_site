package org.example.commerce_site.representation.address;

import org.example.commerce_site.application.address.AddressFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.address.request.AddressRequest;
import org.example.commerce_site.representation.address.response.AddressResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
public class AddressController {
	private final AddressFacade addressFacade;

	@PostMapping()
	public ApiSuccessResponse<AddressResponse.Create> createAddrss(
		@Valid @RequestBody AddressRequest.Create request) {
		return ApiSuccessResponse.success(
			AddressResponse.Create.of(addressFacade.create(AddressRequest.Create.toDto(request))));
	}
}
