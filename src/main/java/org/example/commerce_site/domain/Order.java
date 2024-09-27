package org.example.commerce_site.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.domain.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order extends BaseTimeEntity {
	private Long userId;
	private BigDecimal totalAmount;
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	@OneToMany(mappedBy = "order")
	private List<OrderDetail> orderDetail = new ArrayList<>();
}
