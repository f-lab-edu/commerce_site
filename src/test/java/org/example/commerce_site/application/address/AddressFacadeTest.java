package org.example.commerce_site.application.address;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.application.address.dto.AddressResponseDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Address;
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
class AddressFacadeTest {
	@InjectMocks
	private AddressFacade addressFacade;

	@Mock
	private AddressService addressService;

	@Mock
	private UserService userService;

	private User user = User.builder().id(1L).email("test@test.com").build();

	@Test
	void create_ShouldCreateAddress() {
		AddressRequestDto.Create dto = AddressRequestDto.Create.builder()
			.userAuthId(user.getId())
			.isPrimary(true)
			.build();

		Address address = new Address();

		when(userService.getUser(anyLong())).thenReturn(user);
		when(addressService.createAddress(any(), any())).thenReturn(address);

		addressFacade.create(dto);

		verify(userService).getUser(user.getId());
		verify(addressService).createAddress(dto, user);
	}

	@Test
	void get_ShouldReturnAddress() {
		Long addressId = 1L;
		Address address = Address.builder()
			.id(addressId)
			.jibunAddress("jibun address")
			.phoneNumber("01012341234")
			.build();

		when(userService.getUser(anyLong())).thenReturn(user);
		when(addressService.getAddress(anyLong(), any())).thenReturn(address);

		AddressResponseDto.Get result = addressFacade.get(addressId, user.getId());

		assertThat(result.getJibunAddress(), equalTo("jibun address"));
		assertThat(result.getPhoneNumber(), equalTo("01012341234"));
		verify(userService).getUser(user.getId());
		verify(addressService).getAddress(addressId, user);
	}

	@Test
	void getList_ShouldReturnAddressList() {
		int page = 0;
		int pageSize = 10;
		Address address = new Address();
		Page<Address> addressPage = new PageImpl<>(Collections.singletonList(address));

		when(userService.getUser(anyLong())).thenReturn(user);
		when(addressService.getAddressesByUser(any(), any())).thenReturn(addressPage);

		Page<AddressResponseDto.Get> result = addressFacade.getList(user.getId(), page, pageSize);

		verify(userService).getUser(user.getId());
		verify(addressService).getAddressesByUser(user, PageRequest.of(page, pageSize));
	}

	@Test
	void delete_ShouldDeleteAddress() {
		Long addressId = 1L;

		when(userService.getUser(anyLong())).thenReturn(user);

		addressFacade.delete(user.getId(), addressId);

		verify(userService).getUser(user.getId());
		verify(addressService).deleteAddress(addressId, user);
	}
}