package org.example.commerce_site.attribute;

public enum ShipmentStatus {
	PENDING,        // 주문 확인 중
	SHIPPED,        // 배송 접수
	IN_TRANSIT,     // 배송 중
	DELIVERED,      // 배송 완료
	CANCELLED       // 배송 취소
}
