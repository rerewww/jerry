package com.jerry.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by son on 2019-04-12.
 */
@Slf4j
@Service
public class LinuxSystemService implements SystemService {
	private SystemCommonUtils systemCommonUtils;

	public LinuxSystemService(final SystemCommonUtils systemCommonUtils) {
		this.systemCommonUtils = systemCommonUtils;
	}

	@Override
	public Map<String, Integer> getUsage() {
		List<String> command = new ArrayList<>();
		command.add("vmstat");
		command.add("-s");

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectInput();
		String result = systemCommonUtils.getStringResultByProcess(processBuilder);

		log.info(result);
		return null;
	}

	@Override
	public Map<String, String> getInfos() {
		return systemCommonUtils.getInfos();
	}
}
