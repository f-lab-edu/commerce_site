package org.example.commerce_site.representation.address;

import org.example.commerce_site.application.address.AddressFacade;
import org.example.commerce_site.application.address.dto.AddressResponseDto;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.address.request.AddressRequest;
import org.example.commerce_site.representation.address.response.AddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/address")
public class AddressController {
	private final AddressFacade addressFacade;

	@PostMapping()
	public ApiSuccessResponse createAddrss(
		@Valid @RequestBody AddressRequest.Create request) {
		addressFacade.create(AddressRequest.Create.toDto(request));
		return ApiSuccessResponse.success();
	}

	@GetMapping("/{user_id}/{address_id}")
	public ApiSuccessResponse<AddressResponse.Get> getAddress(
		@PathVariable(name = "user_id") Long userId, //TODO 추후 토큰에서 가져오도록 함
		@PathVariable(name = "address_id") Long addressId) {
		return ApiSuccessResponse.success(
			AddressResponse.Get.of(addressFacade.get(addressId, userId)));
	}

	@GetMapping("/{user_id}/list")
	public ApiSuccessResponse.PageList<AddressResponse.Get> getAddresses(
		@PathVariable(name = "user_id") Long userId, //TODO 추후 토큰에서 가져오도록 함
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size) {
		return ApiSuccessResponse.success(AddressResponse.Get.of(addressFacade.getList(userId, page, size)));
	}

	@PatchMapping("/{user_id}/{address_id}")
	public ApiSuccessResponse updateAddress(
		@PathVariable(name = "user_id") Long userId, //TODO 추후 토큰에서 가져오도록 함
		@PathVariable(name = "address_id") Long addressId,
		@Valid @RequestBody AddressRequest.Update request) {
		addressFacade.update(userId, addressId, AddressRequest.Update.toDto(request));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping("/{user_id}/{address_id}")
	public ApiSuccessResponse deleteAddress(
		@PathVariable(name = "user_id") Long userId, //TODO 추후 토큰에서 가져오도록 함
		@PathVariable(name = "address_id") Long addressId) {
		addressFacade.delete(userId, addressId);
		return ApiSuccessResponse.success();
	}
}
