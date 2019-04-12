package com.jerry.system;

import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by son on 2019-03-09.
 */
@Slf4j
@Service
public class WindowSystemService implements SystemService {
    private SystemCommonUtils systemCommonUtils;

    public WindowSystemService(final SystemCommonUtils systemCommonUtils) {
        this.systemCommonUtils = systemCommonUtils;
    }

    @Override
    public Map<String, Integer> getUsage() {
        Map<String, Integer> result = new HashMap<>();
        OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        double memory = operatingSystemMXBean.getTotalPhysicalMemorySize() - operatingSystemMXBean.getFreePhysicalMemorySize();

        result.put("cpu", systemCommonUtils.getCpuUsage(operatingSystemMXBean));
        result.put("memory", (int)(memory / operatingSystemMXBean.getTotalPhysicalMemorySize() * 100));
        return result;
    }

    @Override
    public Map<String, String> getInfos() {
	    return systemCommonUtils.getInfos();
    }

}
