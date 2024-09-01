package org.example.commerce_site.application.address;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.application.address.dto.AddressResponseDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressFacade {
	private final AddressService addressService;
	private final UserService userService;

	public AddressResponseDto.Create create(AddressRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserId());
		return AddressResponseDto.Create.of(addressService.createAddress(dto, user));
	}
}
