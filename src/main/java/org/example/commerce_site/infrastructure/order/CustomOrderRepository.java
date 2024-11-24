package org.example.commerce_site.infrastructure.order;

import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.common.domain.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository {
	<T extends Account> Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, T user);
}
