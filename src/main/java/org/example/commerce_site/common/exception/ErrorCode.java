package org.example.commerce_site.common.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
	//API RuntimeException
	NOT_FOUND(HttpStatus.NOT_FOUND, 404, "데이터를 찾을 수 없습니다."),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "내부 서버 오류."),
	UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "접근 할수없습니다."),
	BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "잘못된 요청입니다."),
	INVALID_PARAM(HttpStatus.BAD_REQUEST, 400, "잘못된 parameter 입니다."),
	ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "권한이 부족합니다."),
	METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, 405, "허용되지 않은 메소드 입니다."),
	//address
	ADDRESS_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "배송지 정보를 찾을 수 없습니다."),

	//cart
	QUANTITY_IS_ZERO(HttpStatus.BAD_REQUEST, 400, "장바구니에 담는 수량은 0이 될 수 없습니다."),

	//user
	USER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "회원 정보를 찾을 수 없습니다."),
	EMAIL_ALREADY_EXISTED(HttpStatus.NOT_FOUND, 400, "이미 존재하는 이메일입니다."),
	CREATE_KEYCLOAK_USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "인가 서버 유저 등록에 실패했습니다."),
	ADD_USER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "API 서버 유저 등록에 실패했습니다."),

	//order
	ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "주문 정보를 찾을 수 없습니다."),

	//partner
	PARTNER_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "파트너 회원 정보를 찾을 수 없습니다."),

	//product
	PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "상품 정보를 찾을 수 없습니다."),
	PRODUCT_ACCESS_DENIED(HttpStatus.FORBIDDEN, 403, "상품 정보 수정에 대한 권한이 없습니다."),
	PRODUCT_OUT_OF_STOCK(HttpStatus.CONFLICT, 409, "상품의 재고가 부족합니다"),

	//category
	CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "카테고리 정보를 찾을 수 없습니다.");

	private final HttpStatus httpStatus;
	private final int code;
	private final String message;
}
