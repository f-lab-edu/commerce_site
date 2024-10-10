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

	@Test
	public void create_whenQuantityIsZero_throwsException() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userAuthId(1L).productId(2L).quantity(0L).build();

		assertThrows(CustomException.class, () -> cartService.create(createDto),
			"Expected to throw CustomException when quantity is zero.");
	}

	@Test
	public void create_whenCartExists_updatesQuantity() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userAuthId(1L).productId(2L).quantity(1L).build();

		Cart existingCart = Cart.builder()
			.userId(createDto.getUserAuthId())
			.productId(createDto.getProductId())
			.quantity(3L)
			.build();

		when(cartRepository.findByUserIdAndProductId(createDto.getUserAuthId(), createDto.getProductId()))
			.thenReturn(Optional.of(existingCart));

		cartService.create(createDto);

		verify(cartRepository, times(1)).findByUserIdAndProductId(createDto.getUserAuthId(), createDto.getProductId());
		assertEquals(4L, existingCart.getQuantity());
	}

	@Test
	public void create_whenCartDoesNotExist_savesNewCart() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userAuthId(1L).productId(2L).quantity(1L).build();

		when(cartRepository.findByUserIdAndProductId(createDto.getUserAuthId(), createDto.getProductId()))
			.thenReturn(Optional.empty());

		cartService.create(createDto);

		verify(cartRepository, times(1)).findByUserIdAndProductId(createDto.getUserAuthId(), createDto.getProductId());
		verify(cartRepository, times(1)).save(any(Cart.class));
	}

	@Test
	public void delete_ShouldDeleteCart() {
		List<Long> productIds = Arrays.asList(1L, 2L, 3L);
		CartRequestDto.Delete deleteDto = CartRequestDto.Delete.builder()
			.userAuthId(1L)
			.productIds(productIds)
			.build();

		cartService.delete(deleteDto);

		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserAuthId(), 1L);
		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserAuthId(), 2L);
		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserAuthId(), 3L);
	}

	@Test
	void update_ShouldUpdateQuantity() {
		Cart cart = Cart.builder().userId(1L).productId(1L).quantity(1L).build();
		HashMap<Long, Long> productIdAndQuantity = new HashMap<>();
		productIdAndQuantity.put(cart.getProductId(), 3L); // Update quantity to 3

		CartRequestDto.Update dto = CartRequestDto.Update.builder()
			.userAuthId(1L)
			.productsMap(productIdAndQuantity)
			.build();

		when(cartRepository.findByUserIdAndProductIdIn(1L, productIdAndQuantity.keySet()))
			.thenReturn(List.of(cart));

		cartService.update(dto);

		assertEquals(3L, cart.getQuantity());
		verify(cartRepository).saveAll(anyList());
	}

	@Test
	public void update_QuantityIsZero_ThrowCustomException() {
		Cart cart = Cart.builder().userId(1L).productId(1L).quantity(1L).build();
		HashMap<Long, Long> productIdAndQuantity = new HashMap<>();
		productIdAndQuantity.put(cart.getProductId(), 0L); // Quantity set to 0

		CartRequestDto.Update dto = CartRequestDto.Update.builder()
			.userAuthId(1L)
			.productsMap(productIdAndQuantity)
			.build();

		CustomException exception = assertThrows(CustomException.class, () -> {
			cartService.update(dto);
		});

		assertEquals(ErrorCode.QUANTITY_IS_ZERO, exception.getErrorCode());
	}
}