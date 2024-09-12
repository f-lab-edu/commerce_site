package org.example.commerce_site.representation.cart;

import org.example.commerce_site.application.cart.CartFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.cart.request.CartRequest;
import org.example.commerce_site.representation.cart.response.CartResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
	private final CartFacade cartFacade;

	@PostMapping("/{user_id}")
	public ApiSuccessResponse createCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestBody @Valid CartRequest.Create request) {
		cartFacade.create(CartRequest.Create.toDto(request, userId));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping("/{user_id}")
	public ApiSuccessResponse deleteCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestBody @Valid CartRequest.Delete request) {
		cartFacade.delete(CartRequest.Delete.toDto(request, userId));
		return ApiSuccessResponse.success();
	}

	@PatchMapping("/{user_id}")
	public ApiSuccessResponse updateCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestBody @Valid CartRequest.Update request
	) {
		cartFacade.update(CartRequest.Update.toDto(request, userId));
		return ApiSuccessResponse.success();
	}

	@GetMapping("/{user_id}")
	public ApiSuccessResponse.PageList<CartResponse.Get> getCart(
		@RequestParam(name = "user_id") Long userId,
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size
	) {
		return ApiSuccessResponse.success(CartResponse.Get.of(cartFacade.getList(userId, page - 1, size)));
	}
}
