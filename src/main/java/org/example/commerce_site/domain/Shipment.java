package org.example.commerce_site.domain;

import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "shipments")
public class Shipment extends BaseTimeEntity {
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;

	@OneToOne
	@JoinColumn(name = "order_detail_id")
	private OrderDetail orderDetail;

	@OneToOne
	@JoinColumn(name = "address_id")
	private Address address;

	private String trackingNumber;

	@Enumerated(EnumType.STRING)
	private ShipmentStatus status;
}
