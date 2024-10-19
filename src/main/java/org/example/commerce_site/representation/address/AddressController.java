package org.example.commerce_site.representation.address;

import org.example.commerce_site.application.address.AddressFacade;
import org.example.commerce_site.common.auth.UserCheck;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.address.dto.AddressRequest;
import org.example.commerce_site.representation.address.dto.AddressResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/addresses")
public class AddressController {
	private final AddressFacade addressFacade;

	@UserCheck
	@PostMapping()
	public ApiSuccessResponse createAddrss(
		@Valid @RequestBody AddressRequest.Create request,
		@RequestAttribute("userId") String userAuthId) {
		addressFacade.create(AddressRequest.Create.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}

	@UserCheck
	@GetMapping("/{address_id}")
	public ApiSuccessResponse<AddressResponse.Get> getAddress(
		@RequestAttribute("userId") String userAuthId,
		@PathVariable(name = "address_id") Long addressId) {
		return ApiSuccessResponse.success(
			AddressResponse.Get.of(addressFacade.get(addressId, userAuthId)));
	}

	@UserCheck
	@GetMapping()
	public ApiSuccessResponse.PageList<AddressResponse.Get> getAddresses(
		@RequestAttribute("userId") String userAuthId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size) {
		return ApiSuccessResponse.success(AddressResponse.Get.of(addressFacade.getList(userAuthId, page - 1, size)));
	}

	@UserCheck
	@DeleteMapping("/{address_id}")
	public ApiSuccessResponse deleteAddress(
		@RequestAttribute("userId") String userAuthId,
		@PathVariable(name = "address_id") Long addressId) {
		addressFacade.delete(userAuthId, addressId);
		return ApiSuccessResponse.success();
	}
}
