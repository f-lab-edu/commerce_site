package org.example.commerce_site.application.address;

import org.example.commerce_site.application.address.dto.AddressRequestDto;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.AddressRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressRepository addressRepository;

	@Transactional
	public Address createAddress(AddressRequestDto.Create dto, User user) {
		if (dto.getIsPrimary()) {
			addressRepository.findByUserIdAndIsPrimaryTrue(user).ifPresent(address -> {
				address.updatePrimary();
				addressRepository.save(address);
			});
		}
		return addressRepository.save(AddressRequestDto.Create.toEntity(dto, user));
	}
}
