package com.jerry.service;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpSession;

import static org.mockito.Mockito.doReturn;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
/**
 * Created by son on 2019-04-07.
 */
public class AuthServiceTest {
	@InjectMocks
	private AuthService service;

	@Mock private ServerConfig config;
	@Mock private MockHttpSession session;

	private static final String TEST_USER_ID = "TEST_USER_ID";

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new AuthService(config);
	}

	@Test
	public void createUniqueKey() {
		System.out.println(service.createUniqueKey(TEST_USER_ID));
	}

	@Test
	public void checkAuthTest() {
		doReturn("userId").doReturn("userId2").when(config).getAdminUserId();
		doReturn("password").when(config).getAdminUserPassword();
		assertThat(service.checkAuth("userId","password"), is(true));
		assertThat(service.checkAuth("userId","password"), is(false));
	}

	@Test
	public void checkSessionTest() {
		assertThat(service.checkSession("userId", "userId"), is(true));
	}
}
