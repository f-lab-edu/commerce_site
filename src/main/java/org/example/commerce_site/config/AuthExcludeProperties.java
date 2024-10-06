package org.example.commerce_site.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth.exclude")
public class AuthExcludeProperties {
	private String[] post;
	private String[] get;
	private String[] web;
}

