package org.example.commerce_site.representation.cart;

import org.example.commerce_site.application.cart.CartFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.cart.dto.CartRequest;
import org.example.commerce_site.representation.cart.dto.CartResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
@PreAuthorize("hasAuthority('ROLE_USER')")
public class CartController {
	private final CartFacade cartFacade;

	@PostMapping()
	public ApiSuccessResponse createCart(
		@RequestAttribute("userId") String userAuthId,
		@RequestBody @Valid CartRequest.Create request) {
		cartFacade.create(CartRequest.Create.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping()
	public ApiSuccessResponse deleteCart(
		@RequestAttribute("userId") String userAuthId,
		@RequestBody @Valid CartRequest.Delete request) {
		cartFacade.delete(CartRequest.Delete.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}

	@PatchMapping()
	public ApiSuccessResponse updateCart(
		@RequestAttribute("userId") String userAuthId,
		@RequestBody @Valid CartRequest.Update request
	) {
		cartFacade.update(CartRequest.Update.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}

	@GetMapping()
	public ApiSuccessResponse.PageList<CartResponse.Get> getCart(
		@RequestAttribute("userId") String userAuthId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		return ApiSuccessResponse.success(CartResponse.Get.of(cartFacade.getList(userAuthId, page - 1, size)));
	}
}
