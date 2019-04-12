package com.jerry.system;

import com.jerry.config.ServerConfig;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

/**
 * Created by son on 2019-04-12.
 */
public class LinuxSystemServiceTest {
	@InjectMocks
	private LinuxSystemService service;

	@Mock ServerConfig config;
	@Mock SystemCommonUtils systemCommonUtils;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		service = new LinuxSystemService(systemCommonUtils);
	}

	@Test
	public void getUsage() {
		doReturn("16265188").doReturn("5793136").when(systemCommonUtils).getStringResultByProcess(any(ProcessBuilder.class));

		Map<String, Integer> usage = service.getUsage();
		for (Map.Entry<String, Integer> entry : usage.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}

}
