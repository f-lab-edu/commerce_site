package org.example.commerce_site.config.filter;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserIdFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		if (request.getRequestURI().equals("/auth/callback")
			|| request.getRequestURI().equals("/users/keycloak/webhook")
			|| request.getRequestURI().contains("/swagger-ui")
			|| request.getRequestURI().contains("/api-docs")
		) {
			filterChain.doFilter(request, response);
			return;
		}

		var authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			String userId = ((JwtAuthenticationToken)authentication).getToken().getSubject();
			request.setAttribute("userId", userId);
		}

		filterChain.doFilter(request, response);
	}
}
