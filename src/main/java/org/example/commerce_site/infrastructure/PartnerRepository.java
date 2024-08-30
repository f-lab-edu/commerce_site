package org.example.commerce_site.infrastructure;

import org.example.commerce_site.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartnerRepository extends JpaRepository<Partner, Integer> {
}
