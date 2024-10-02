package org.example.commerce_site.infrastructure.user;

import java.util.Optional;

import org.example.commerce_site.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByAuthId(String userAuthId);
}
