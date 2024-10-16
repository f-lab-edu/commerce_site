package org.example.commerce_site.infrastructure.partner;

import java.util.Optional;

import org.example.commerce_site.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
	Optional<Partner> findByEmail(String email);

	Optional<Partner> findByAuthId(String partnerAuthId);
}
