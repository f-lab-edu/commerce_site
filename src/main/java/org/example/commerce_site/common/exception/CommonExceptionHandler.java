package org.example.commerce_site.common.exception;

import org.example.commerce_site.common.response.ApiFailResponse;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

	@ExceptionHandler({MissingServletRequestParameterException.class, ServletRequestBindingException.class,
		TypeMismatchException.class, HttpMessageNotReadableException.class, MissingServletRequestPartException.class,
		HttpMessageNotReadableException.class, MethodArgumentNotValidException.class})
	protected ResponseEntity<ApiSuccessResponse> handleBadRequestException(final Exception e) {
		ErrorCode errorCode = ErrorCode.BAD_REQUEST;
		log.warn("[BadRequestException] error code : {}, error message : {}", errorCode,
			NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		return ApiFailResponse.fail(errorCode);
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	protected ResponseEntity<ApiSuccessResponse> handleHttpRequestMethodNotSupportedException(
		final HttpRequestMethodNotSupportedException e) {
		log.error("[HttpRequestMethodNotSupportedException]", e);
		ErrorCode errorCode = ErrorCode.METHOD_NOT_ALLOWED;
		return ApiFailResponse.fail(errorCode);
	}

	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<ApiSuccessResponse> handleCustomException(CustomException e) {
		ErrorCode errorCode = e.getErrorCode();
		log.warn("[CustomException] error code : {}, error message : {}", errorCode,
			NestedExceptionUtils.getMostSpecificCause(e).getMessage());
		return ApiFailResponse.fail(errorCode);
	}
}