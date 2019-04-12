package com.jerry.system;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

/**
 * Created by son on 2019-04-12.
 */
public class LinuxSystemServiceTest {
	private LinuxSystemService service;

	@Mock ServerConfig config;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new LinuxSystemService(new SystemCommonUtils(config));
	}

	@Test
	public void getUsage() {
		Map<String, Integer> usage = service.getUsage();
		for (Map.Entry<String, Integer> entry : usage.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

}
