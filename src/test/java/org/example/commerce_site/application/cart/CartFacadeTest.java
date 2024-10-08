package org.example.commerce_site.application.cart;

import static org.mockito.Mockito.*;

import java.util.Collections;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CartFacadeTest {

	@Mock
	private CartService cartService;

	@Mock
	private UserService userService;

	@Mock
	private ProductService productService;

	@InjectMocks
	private CartFacade cartFacade;

	@Test
	void create_ShouldCreateCart() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userId(1L)
			.productId(1L)
			.build();

		cartFacade.create(createDto);

		verify(userService).getUser(createDto.getUserId());
		verify(productService).getProduct(createDto.getProductId());
		verify(cartService).create(createDto);
	}

	@Test
	void delete_ShouldDeleteCart() {
		CartRequestDto.Delete deleteDto = CartRequestDto.Delete.builder()
			.userId(1L)
			.build();

		cartFacade.delete(deleteDto);

		verify(userService).getUser(deleteDto.getUserId());
		verify(cartService).delete(deleteDto);
	}

	@Test
	void update_ShouldUpdateCart() {
		CartRequestDto.Update updateDto = CartRequestDto.Update.builder()
			.userId(1L)
			.build();

		cartFacade.update(updateDto);

		verify(userService).getUser(updateDto.getUserId());
		verify(cartService).update(updateDto);
	}

	@Test
	void get_ShouldReturnAddressList() {
		// given
		Long userId = 1L;
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<CartResponseDto.Get> cartList = new PageImpl<>(Collections.emptyList());

		when(cartService.getList(userId, pageRequest)).thenReturn(cartList);

		cartFacade.getList(userId, page, size);

		verify(userService).getUser(userId);
		verify(cartService).getList(userId, pageRequest);
	}
}