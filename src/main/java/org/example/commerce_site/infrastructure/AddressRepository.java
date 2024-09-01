package org.example.commerce_site.infrastructure;

import java.util.Optional;

import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<Address> findByUserIdAndIsPrimaryTrue(User userId);
}
