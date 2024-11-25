package org.example.commerce_site.infrastructure.order;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.common.domain.Account;
import org.example.commerce_site.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomOrderRepository {
	<T extends Account> Page<OrderResponseDto.Get> getOrders(Pageable pageable, String keyword, T user);

	List<OrderResponseDto.GetOneOff> getOrderListByUserIdAndProductId(User user, Long productId);
}
