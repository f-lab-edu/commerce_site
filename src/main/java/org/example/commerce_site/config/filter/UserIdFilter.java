package org.example.commerce_site.config.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import org.example.commerce_site.config.AuthExcludeProperties;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class UserIdFilter extends OncePerRequestFilter {
	private final AuthExcludeProperties excludeProperties;

	public UserIdFilter(AuthExcludeProperties excludeProperties) {
		this.excludeProperties = excludeProperties;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
		throws ServletException, IOException {

		String requestURI = request.getRequestURI();
		boolean isExcluded = Stream.of(excludeProperties.getPost(), excludeProperties.getGet(),
				excludeProperties.getWeb())
			.flatMap(Arrays::stream)
			.anyMatch(path -> requestURI.equals(path) || requestURI.startsWith(path.replace("**", "")));

		if (isExcluded) {
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
