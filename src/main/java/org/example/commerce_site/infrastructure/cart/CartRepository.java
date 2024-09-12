package org.example.commerce_site.infrastructure.cart;

import java.util.Optional;

import org.example.commerce_site.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	void deleteByUserIdAndProductId(Long userId, Long productId);

	Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);
}
