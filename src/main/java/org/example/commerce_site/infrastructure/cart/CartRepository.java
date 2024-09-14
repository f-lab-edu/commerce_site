package org.example.commerce_site.infrastructure.cart;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.example.commerce_site.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
	void deleteByUserIdAndProductId(Long userId, Long productId);

	Optional<Cart> findByUserIdAndProductId(Long userId, Long productId);

	List<Cart> findByUserIdAndProductIdIn(Long userId, Set<Long> productId);
}
