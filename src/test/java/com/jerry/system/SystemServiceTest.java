package com.jerry.system;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/**
 * Created by son on 2019-03-09.
 */
public class SystemServiceTest {
	@Mock ServerConfig serverConfig;
	private WindowSystemService systemService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		systemService = new WindowSystemService(new SystemCommonUtils(serverConfig));
	}

	@Test
	public void getUsageTest() {
		Map<String, Integer> result = systemService.getUsage();
		System.out.println(result.get("cpu"));
		System.out.println(result.get("memory"));
	}
}
