package org.example.commerce_site.application.cart;

import static org.mockito.Mockito.*;

import java.util.Collections;

import org.example.commerce_site.application.cart.dto.CartRequestDto;
import org.example.commerce_site.application.cart.dto.CartResponseDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Product;
import org.example.commerce_site.domain.User;
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

	private User user = User.builder().id(1L).authId("testAuth").build();

	@Test
	void create_ShouldCreateCart() {
		CartRequestDto.Create createDto = CartRequestDto.Create.builder()
			.userAuthId("testAuth")
			.productId(1L)
			.build();

		when(userService.getUser(createDto.getUserAuthId())).thenReturn(user);
		Product product = new Product();
		when(productService.getProduct(createDto.getProductId())).thenReturn(product);

		cartFacade.create(createDto);

		verify(userService).getUser(createDto.getUserAuthId());
		verify(productService).getProduct(createDto.getProductId());
		verify(cartService).create(createDto, user, product);
	}

	@Test
	void delete_ShouldDeleteCart() {
		CartRequestDto.Delete deleteDto = CartRequestDto.Delete.builder()
			.userAuthId("testAuth")
			.build();
		when(userService.getUser(deleteDto.getUserAuthId())).thenReturn(user);

		cartFacade.delete(deleteDto);

		verify(userService).getUser(deleteDto.getUserAuthId());
		verify(cartService).delete(deleteDto, user);
	}

	@Test
	void update_ShouldUpdateCart() {
		CartRequestDto.Update updateDto = CartRequestDto.Update.builder()
			.userAuthId("testAuth")
			.build();
		when(userService.getUser(updateDto.getUserAuthId())).thenReturn(user);

		cartFacade.update(updateDto);

		verify(userService).getUser(updateDto.getUserAuthId());
		verify(cartService).update(updateDto, user);
	}

	@Test
	void get_ShouldReturnAddressList() {
		String userAuthId = "testAuth";
		Long userId = 1L;
		int page = 0;
		int size = 10;
		PageRequest pageRequest = PageRequest.of(page, size);
		Page<CartResponseDto.Get> cartList = new PageImpl<>(Collections.emptyList());

		when(userService.getUser(userAuthId)).thenReturn(user);
		when(cartService.getList(userId, pageRequest)).thenReturn(cartList);

		Page<CartResponseDto.Get> result = cartFacade.getList(userAuthId, page, size);

		verify(userService).getUser(userAuthId);
		verify(cartService).getList(userId, pageRequest);
	}
}