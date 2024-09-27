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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CartServiceTest {
	@InjectMocks
	private CartService cartService;

	@Mock
	private CartRepository cartRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testCreate_whenQuantityIsZero_throwsException() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userId(1L).productId(2L).quantity(0L).build();

		assertThrows(CustomException.class, () -> cartService.create(createDto),
			"Expected to throw CustomException when quantity is zero.");
	}

	@Test
	public void testCreate_whenCartExists_updatesQuantity() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userId(1L).productId(2L).quantity(1L).build();

		Cart existingCart = Cart.builder()
			.userId(createDto.getUserId())
			.productId(createDto.getProductId())
			.quantity(3L)
			.build();

		when(cartRepository.findByUserIdAndProductId(createDto.getUserId(), createDto.getProductId()))
			.thenReturn(Optional.of(existingCart));

		cartService.create(createDto);

		verify(cartRepository, times(1)).findByUserIdAndProductId(createDto.getUserId(), createDto.getProductId());
		assertEquals(4L, existingCart.getQuantity());
	}

	@Test
	public void testCreate_whenCartDoesNotExist_savesNewCart() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userId(1L).productId(2L).quantity(1L).build();

		when(cartRepository.findByUserIdAndProductId(createDto.getUserId(), createDto.getProductId()))
			.thenReturn(Optional.empty());

		cartService.create(createDto);

		verify(cartRepository, times(1)).findByUserIdAndProductId(createDto.getUserId(), createDto.getProductId());
		verify(cartRepository, times(1)).save(any(Cart.class));
	}

	@Test
	public void testDelete() {
		List<Long> productIds = Arrays.asList(1L, 2L, 3L);
		CartRequestDto.Delete deleteDto = CartRequestDto.Delete.builder()
			.userId(1L)
			.productIds(productIds)
			.build();

		cartService.delete(deleteDto);

		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserId(), 1L);
		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserId(), 2L);
		verify(cartRepository, times(1)).deleteByUserIdAndProductId(deleteDto.getUserId(), 3L);
	}

	@Test
	public void testUpdate_ExistingCart_Success() {
		HashMap<Long, Long> productMap = new HashMap<>();
		productMap.put(1L, 5L);
		Cart existingCart = Cart.builder().userId(1L).productId(1L).quantity(3L).build();

		CartRequestDto.Update updateDto = CartRequestDto.Update.builder()
			.userId(1L)
			.productsMap(productMap)
			.build();

		when(cartRepository.findByUserIdAndProductId(updateDto.getUserId(), 1L)).thenReturn(Optional.of(existingCart));

		cartService.update(updateDto);

		verify(cartRepository).findByUserIdAndProductId(updateDto.getUserId(), 1L);
		verify(cartRepository).save(existingCart);
		assertEquals(5L, existingCart.getQuantity());
	}

	@Test
	public void testUpdate_QuantityIsZero_ThrowCustomException() {
		HashMap<Long, Long> productMap = new HashMap<>();
		productMap.put(1L, 0L);
		CartRequestDto.Update updateDto = CartRequestDto.Update.builder()
			.userId(1L)
			.productsMap(productMap)
			.build();

		Cart existingCart = Cart.builder().userId(1L).productId(1L).quantity(3L).build();

		when(cartRepository.findByUserIdAndProductId(updateDto.getUserId(), 1L)).thenReturn(Optional.of(existingCart));

		CustomException thrown = assertThrows(CustomException.class, () -> cartService.update(updateDto));
		assertEquals(ErrorCode.QUANTITY_IS_ZERO, thrown.getErrorCode());
		verify(cartRepository, never()).save(any());
	}

	@Test
	public void testUpdate_NonExistingCart_DoNothing() {
		HashMap<Long, Long> productMap = new HashMap<>();
		productMap.put(1L, 5L);
		CartRequestDto.Update updateDto = CartRequestDto.Update.builder()
			.userId(1L)
			.productsMap(productMap)
			.build();

		when(cartRepository.findByUserIdAndProductId(updateDto.getUserId(), 1L)).thenReturn(Optional.empty());

		cartService.update(updateDto);

		verify(cartRepository).findByUserIdAndProductId(updateDto.getUserId(), 1L);
		verify(cartRepository, never()).save(any());
	}
}