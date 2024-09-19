package org.example.commerce_site.attribute;

public enum OrderStatus {
	PENDING(1),        // 주문이 접수되었으나 처리되지 않은 상태
	CONFIRMED(2),      // 주문이 확인되었으며, 처리 중인 상태
	SHIPPED(3),        // 주문이 발송된 상태
	DELIVERED(4),      // 주문이 고객에게 전달된 상태
	CANCELLED(5),      // 주문이 취소된 상태
	RETURNED(6),       // 주문이 반품된 상태
	REFUNDED(7)        // 주문이 환불된 상태
	;

	private int phase;

	OrderStatus(int phase) {
		this.phase = phase;
	}

	public boolean isPhaseCanCancelOrder(OrderStatus orderStatus) {
		if (orderStatus == PENDING || orderStatus == CONFIRMED) {
			return true;
		} else {
			return false;
		}
	}
}
