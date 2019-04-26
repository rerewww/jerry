package com.jerry.service;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by son on 2019-04-07.
 */
public class AuthServiceTest {
	@InjectMocks
	private AuthService service;

	@Mock private ServerConfig config;

	private String TEST_USER_ID = "TEST_USER_ID";
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new AuthService(config);
	}

	@Test
	public void createUniqueKey() {
		System.out.println(service.createUniqueKey(TEST_USER_ID));
	}
}
