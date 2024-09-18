package org.example.commerce_site.infrastructure.address;

import java.util.Optional;

import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByUserIdAndIsPrimaryTrue(User user);

	Page<Address> findAllByUserId(User user, PageRequest pageRequest);

	Optional<Address> findByIdAndUserId(Long addressId, User userId);

	void deleteByIdAndUserId(Long addressId, User userId);
}
