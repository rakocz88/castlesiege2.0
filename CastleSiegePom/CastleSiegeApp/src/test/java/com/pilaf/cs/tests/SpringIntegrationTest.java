package com.pilaf.cs.tests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class SpringIntegrationTest {

	@LocalServerPort
	public int port;
	
	@Value("${jwt.header}")
	public String path;

	@Value("${jwt.route.authentication.path}")
	public String loginURL;

	@Value("${jwt.route.authentication.refresh}")
	public String refreshURL;

}
