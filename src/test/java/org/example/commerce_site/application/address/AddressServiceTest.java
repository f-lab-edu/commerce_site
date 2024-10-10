package org.example.commerce_site.application.address;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.Optional;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.address.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {
	@InjectMocks
	private AddressService addressService;

	@Mock
	private AddressRepository addressRepository;

	private User user;

	@BeforeEach
	void setUp() {
		user = User.builder().id(1L).email("test@test.com").build();
	}

	@Test
	void create_ShouldCreateAddress() {
		AddressRequestDto.Create dto = AddressRequestDto.Create.builder()
			.isPrimary(false)
			.userAuthId(user.getId())
			.build();

		when(addressRepository.save(any(Address.class))).thenAnswer(invocation -> invocation.getArgument(0));

		Address result = addressService.createAddress(dto, user);

		verify(addressRepository).save(any(Address.class));
	}

	@Test
	void updatePrimary_ShouldUpdateExistingPrimaryAddress() {
		User user = new User();
		Address existingAddress = new Address();
		existingAddress.updatePrimary(true);

		when(addressRepository.findByUserIdAndIsPrimaryTrue(user)).thenReturn(Optional.of(existingAddress));

		addressService.updatePrimary(user, false);

		verify(addressRepository).save(existingAddress);
	}

	@Test
	void updatePrimary_ShouldNotUpdate_WhenNoExistingPrimaryAddress() {
		User user = new User();

		when(addressRepository.findByUserIdAndIsPrimaryTrue(user)).thenReturn(Optional.empty());

		addressService.updatePrimary(user, true);

		verify(addressRepository, never()).save(any(Address.class)); // 기본 주소가 없을 때는 save가 호출되지 않음
	}

	@Test
	void get_ShouldReturnAddress() {
		Long addressId = 1L;
		Address address = Address.builder().userId(user).build();
		when(addressRepository.findByIdAndUserId(addressId, user)).thenReturn(Optional.of(address));

		Address result = addressService.getAddress(addressId, user);
		verify(addressRepository).findByIdAndUserId(addressId, user);
	}

	@Test
	void get_ShouldThrowException_WhenAddressNotFound() {
		Long addressId = 1L;

		when(addressRepository.findByIdAndUserId(addressId, user)).thenReturn(Optional.empty());

		CustomException exception = org.junit.jupiter.api.Assertions.assertThrows(
			CustomException.class,
			() -> addressService.getAddress(addressId, user)
		);

		verify(addressRepository).findByIdAndUserId(addressId, user);
		org.junit.jupiter.api.Assertions.assertEquals(ErrorCode.ADDRESS_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	void get_ShouldReturnAddressPage() {
		PageRequest pageRequest = PageRequest.of(0, 10);
		Address address = new Address();
		Page<Address> addressPage = new PageImpl<>(Collections.singletonList(address));

		when(addressRepository.findAllByUserId(user, pageRequest)).thenReturn(addressPage);

		Page<Address> result = addressService.getAddressesByUser(user, pageRequest);

		verify(addressRepository).findAllByUserId(user, pageRequest);
	}

	@Test
	void delete_ShouldDeleteAddress() {
		Long addressId = 1L;

		addressService.deleteAddress(addressId, user);

		verify(addressRepository).deleteByIdAndUserId(addressId, user);
	}
}