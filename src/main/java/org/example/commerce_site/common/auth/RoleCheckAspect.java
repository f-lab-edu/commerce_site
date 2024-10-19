package org.example.commerce_site.common.auth;

import java.util.List;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.commerce_site.attribute.UserRoles;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.common.util.JwtUtil;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@RequiredArgsConstructor
@Component
public class RoleCheckAspect {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BEARER_PREFIX = "Bearer ";
	private final HttpServletRequest httpServletRequest;
	private final JwtUtil jwtUtil;

	@Around("@annotation(org.example.commerce_site.common.auth.PartnerCheck)")
	public Object checkPartner(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkRole(joinPoint, UserRoles.ROLE_PARTNER);
	}

	@Around("@annotation(org.example.commerce_site.common.auth.UserCheck)")
	public Object checkUser(ProceedingJoinPoint joinPoint) throws Throwable {
		return checkRole(joinPoint, UserRoles.ROLE_USER);
	}

	private Object checkRole(ProceedingJoinPoint joinPoint, UserRoles requiredRole) throws Throwable {
		String token = extractTokenFromRequest();
		Jwt jwt = jwtUtil.decodeToken(token);
		List<String> roleList = extractAuthorities(jwt);

		if (!roleList.contains(requiredRole.name())) {
			throw new CustomException(ErrorCode.ACCESS_DENIED);
		}

		return joinPoint.proceed();
	}

	private String extractTokenFromRequest() {
		String authorizationHeader = httpServletRequest.getHeader(AUTHORIZATION);
		if (authorizationHeader == null || !authorizationHeader.startsWith(BEARER_PREFIX)) {
			throw new CustomException(ErrorCode.ACCESS_DENIED);
		}
		return authorizationHeader.substring(BEARER_PREFIX.length());
	}

	private List<String> extractAuthorities(Jwt jwt) {
		var resourceAccess = (Map<String, Object>)jwt.getClaim("resource_access");
		var roles = (Map<String, Object>)resourceAccess.get("oauth2-client-app");
		return (List<String>)roles.get("roles");
	}
}
