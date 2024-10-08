package org.example.commerce_site.application.address;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.address.AddressRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressRepository addressRepository;

	@Transactional
	public Address createAddress(AddressRequestDto.Create dto, User user) {
		return addressRepository.save(AddressRequestDto.Create.toEntity(dto, user));
	}

	@Transactional(readOnly = true)
	public Address getAddress(Long addressId, User user) {
		return addressRepository.findByIdAndUserId(addressId, user)
			.orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public Page<Address> getAddressesByUser(User user, PageRequest pageRequest) {
		return addressRepository.findAllByUserId(user, pageRequest);
	}

	@Transactional
	public void deleteAddress(Long addressId, User user) {
		addressRepository.deleteByIdAndUserId(addressId, user);
	}

	@Transactional
	public void updatePrimary(User user, Boolean isPrimary) {
		addressRepository.findByUserIdAndIsPrimaryTrue(user).ifPresent(address -> {
			address.updatePrimary(isPrimary);
			addressRepository.save(address);
		});
	}
}
