package com.jerry.system;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
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
		Map<String, Integer> result = new HashMap<>();
		ProcessBuilder processBuilderForTotalMem = new ProcessBuilder("/bin/sh", "-c", "vmstat -s | grep -P \"total memory\" | sed 's/[^0-9]//g'");
		processBuilderForTotalMem.redirectInput();
		String totalMemory = systemCommonUtils.getStringResultByProcess(processBuilderForTotalMem);

		ProcessBuilder processBuilderForUsedMem = new ProcessBuilder("/bin/sh", "-c", "vmstat -s | grep -P \"used memory\" | sed 's/[^0-9]//g'");
		processBuilderForUsedMem.redirectInput();
		String usedMemory = systemCommonUtils.getStringResultByProcess(processBuilderForUsedMem);

		OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
		result.put("memory", (int)Math.ceil((Double.valueOf(usedMemory) / Double.valueOf(totalMemory)) * 100.0));
		result.put("cpu", systemCommonUtils.getCpuUsage(operatingSystemMXBean));
		return result;
	}

	@Override
	public Map<String, String> getInfos() {
		return systemCommonUtils.getInfos();
	}
}
