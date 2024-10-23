package org.example.commerce_site.infrastructure.shipment;

import java.util.Optional;

import org.example.commerce_site.domain.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Long> {
	Optional<Shipment> findByOrderDetailId(Long orderDetailId);
}
