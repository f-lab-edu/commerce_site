package org.example.commerce_site.application.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Cart;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.cart.CartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {
	@InjectMocks
	private CartService cartService;

	@Mock
	private CartRepository cartRepository;

	private Product product = Product.builder().id(1L).build();

	private User user = User.builder().id(1L).authId("testAuth").build();

	@Test
	public void create_whenQuantityIsZero_throwsException() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userAuthId("testAuth")
			.productId(2L)
			.quantity(0L)
			.build();

		assertThrows(CustomException.class, () -> cartService.create(createDto, user, product),
			"Expected to throw CustomException when quantity is zero.");
	}

	@Test
	public void create_whenCartExists_updatesQuantity() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.productId(product.getId())
			.quantity(1L)
			.build();

		Cart existingCart = Cart.builder()
			.userId(user.getId())
			.productId(product.getId())
			.quantity(3L)
			.build();

		when(cartRepository.findByUserIdAndProductId(user.getId(), product.getId())).thenReturn(
			Optional.of(existingCart));

		cartService.create(createDto, user, product);

		verify(cartRepository, times(1))
			.findByUserIdAndProductId(user.getId(), product.getId());
		assertEquals(4L, existingCart.getQuantity());
	}

	@Test
	public void create_whenCartDoesNotExist_savesNewCart() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.productId(1L)
			.quantity(1L)
			.build();

		when(cartRepository.findByUserIdAndProductId(eq(user.getId()), eq(product.getId()))).thenReturn(
			Optional.empty());

		cartService.create(createDto, user, product);

		verify(cartRepository, times(1)).findByUserIdAndProductId(user.getId(), product.getId());
		verify(cartRepository, times(1)).save(any(Cart.class)); // Cart 객체가 저장되는지 확인
	}

	@Test
	public void delete_ShouldDeleteCart() {
		List<Long> productIds = Arrays.asList(1L, 2L, 3L);
		CartRequestDto.Delete deleteDto = CartRequestDto.Delete.builder()
			.userAuthId("testAuth") // userAuthId should be String
			.productIds(productIds)
			.build();

		cartService.delete(deleteDto, user); // Pass user

		for (Long productId : productIds) {
			verify(cartRepository, times(1)).deleteByUserIdAndProductId(user.getId(), productId);
		}
	}

	@Test
	void update_ShouldUpdateQuantity() {
		Cart cart = Cart.builder().userId(1L).productId(1L).quantity(1L).build();
		HashMap<Long, Long> productIdAndQuantity = new HashMap<>();
		productIdAndQuantity.put(cart.getProductId(), 3L); // Update quantity to 3

		CartRequestDto.Update dto = CartRequestDto.Update.builder()
			.userAuthId("testAuth") // userAuthId should be String
			.productsMap(productIdAndQuantity)
			.build();

		when(cartRepository.findByUserIdAndProductIdIn(1L, productIdAndQuantity.keySet()))
			.thenReturn(List.of(cart));

		cartService.update(dto, user); // Pass user

		assertEquals(3L, cart.getQuantity());
		verify(cartRepository).saveAll(anyList());
	}

	@Test
	public void update_QuantityIsZero_ThrowCustomException() {
		Cart cart = Cart.builder().userId(1L).productId(1L).quantity(1L).build();
		HashMap<Long, Long> productIdAndQuantity = new HashMap<>();
		productIdAndQuantity.put(cart.getProductId(), 0L); // Quantity set to 0

		CartRequestDto.Update dto = CartRequestDto.Update.builder()
			.userAuthId("testAuth") // userAuthId should be String
			.productsMap(productIdAndQuantity)
			.build();

		CustomException exception = assertThrows(CustomException.class, () -> {
			cartService.update(dto, user); // Pass user
		});

		assertEquals(ErrorCode.QUANTITY_IS_ZERO, exception.getErrorCode());
	}
}