package org.example.commerce_site.application.address;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.application.address.dto.AddressResponseDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.representation.address.request.AddressRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressFacade {
	private final AddressService addressService;
	private final UserService userService;

	public void create(AddressRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserId());
		AddressResponseDto.Get.of(addressService.createAddress(dto, user));
	}

	public AddressResponseDto.Get get(Long addressId, Long userId) {
		User user = userService.getUser(userId);
		return AddressResponseDto.Get.of(addressService.getAddress(addressId, user));
	}

	public Page<AddressResponseDto.Get> getList(Long userId, int page, int pageSize) {
		User user = userService.getUser(userId);
		Page<Address> addressPage = addressService.getAddressesByUser(user, PageRequest.of(page, pageSize));
		return AddressResponseDto.Get.of(addressPage);
	}

	public void update(Long userId, Long addressId, AddressRequestDto.Update dto) {
		User user = userService.getUser(userId);
		Address address = addressService.getAddress(addressId, user);
		addressService.updateAddress(address, dto);
	}

	public void delete(Long userId, Long addressId) {
		User user = userService.getUser(userId);
		addressService.deleteAddress(addressId, user);
	}
}
