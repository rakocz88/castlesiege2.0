package com.pilaf.cs.tests;

import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TestConfiguration {

	@Bean
	public TestRestTemplate testRestTemplate() {
		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpClients.createDefault());
		TestRestTemplate restTemplate = new TestRestTemplate(new RestTemplate(requestFactory));
		return restTemplate;
	}

}
