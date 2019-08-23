package com.jerry.system;

import com.jerry.config.ServerConfig;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by son on 2019-04-12.
 */
@Slf4j
@Service
public class SystemCommonUtils {
	private ServerConfig config;

	public SystemCommonUtils(final ServerConfig config) {
		this.config = config;
	}

	public Map<String, String> getInfos() {
		Pattern pattern = Pattern.compile("Servernumber:([0-9].*)");
		Map<String, String> result = new HashMap<>();

		String version = getTomcatVersion().replaceAll(" ", "");
		Matcher matcher = pattern.matcher(version);

		result.put("version", "no exist");
		if (matcher.find()) {
			result.put("version", matcher.group(1));
		}

		String branch = getBranchInfo().replaceAll("\\n", "");
		result.put("branch", StringUtils.isEmpty(branch) ? "no exist" : branch);
		return result;
	}


	private String getTomcatVersion() {
		if (System.getProperty("os.name").toLowerCase().contains("window")) {
			return "false";
		}

		File tomcatDir = new File(config.getLogFilePath()).getParentFile().getParentFile();
		File versionScript = new File(String.format("%s/bin/version.sh", tomcatDir.getAbsolutePath()));

		List<String> command = new ArrayList<>();
		command.add("/bin/bash");
		command.add("-c");
		command.add(versionScript.getAbsolutePath());

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.redirectInput();

		String result = getStringResultByProcess(processBuilder);
		if (StringUtils.isEmpty(result)) {
			log.warn("result is null");
			return "";
		}
		return result;
	}

	public  String getStringResultByProcess(ProcessBuilder processBuilder) {
		try {
			Process process = processBuilder.start();
			String result = StreamUtils.copyToString(process.getInputStream(), StandardCharsets.UTF_8);
			process.destroy();

			return result;
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
		return null;
	}

	private String getBranchInfo() {
		if (System.getProperty("os.name").toLowerCase().contains("window")) {
			return "false";
		}

		File projectDir = new File(config.getSourceDirPath());

		List<String> command = new ArrayList<>();
		command.add("git");
		command.add("rev-parse");
		command.add("--abbrev-ref");
		command.add("HEAD");

		ProcessBuilder processBuilder = new ProcessBuilder(command);
		processBuilder.directory(projectDir);
		processBuilder.redirectInput();

		String result = getStringResultByProcess(processBuilder);
		if (StringUtils.isEmpty(result)) {
			log.warn("result is null");
			return "";
		}

		return result;
	}

	public int getCpuUsage(OperatingSystemMXBean operatingSystemMXBean) {
		int cpu = 0;
		while (cpu <= 0.0) {
			cpu = (int) Math.round(operatingSystemMXBean.getSystemCpuLoad() * 100.0);
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				log.warn(e.getMessage(), e);
			}
		}
		return cpu;
	}
}
