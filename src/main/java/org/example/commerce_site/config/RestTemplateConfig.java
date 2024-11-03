package org.example.commerce_site.config;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManager;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class RestTemplateConfig {
	@Bean
	RestTemplate restTemplate() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(100);
		connectionManager.setDefaultMaxPerRoute(30);

		RequestConfig requestConfig = RequestConfig.custom()
			.setResponseTimeout(5000, TimeUnit.MILLISECONDS)
			.build();

		CloseableHttpClient httpClient = HttpClientBuilder.create()
			.setDefaultRequestConfig(requestConfig)
			.setConnectionManager(connectionManager)
			.build();

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setHttpClient(httpClient);

		RestTemplate restTemplate = new RestTemplate(factory);

		restTemplate.setErrorHandler(new ResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) throws IOException {
				return response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError();
			}

			@Override
			public void handleError(ClientHttpResponse response) throws IOException {
				log.error("Error occurred : {} ", response.getStatusCode());
				log.error("Error response message : {}", response.getBody());
				throw new CustomException(ErrorCode.REST_TEMPLATE_CONNECTION_ERROR);
			}
		});

		return restTemplate;
	}
}
