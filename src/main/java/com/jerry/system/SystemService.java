package com.jerry.system;

import com.jerry.config.ServerConfig;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by son on 2019-03-09.
 */
@Slf4j
@Service
public class SystemService {
    private ServerConfig config;

    @Autowired
    public SystemService(final ServerConfig config) {
        this.config = config;
    }

    public Map<String, Integer> getUsage() {
        Map<String, Integer> result = new HashMap<>();
        OperatingSystemMXBean operatingSystemMXBean = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        int cpu = 0;

        while (cpu <= 0.0) {
            cpu = (int) Math.round(operatingSystemMXBean.getSystemCpuLoad() * 100.0);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.warn(e.getMessage(), e);
            }
        }

        double memory = operatingSystemMXBean.getTotalPhysicalMemorySize() - operatingSystemMXBean.getFreePhysicalMemorySize();

        result.put("cpu", cpu);
        result.put("memory", (int)(memory / operatingSystemMXBean.getTotalPhysicalMemorySize() * 100));
        return result;
    }

    public String tomcatVersion() {
        // ../bin
        File binDir = new File(config.getLogFilePath()).getParentFile().getParentFile();
        File script = new File(binDir.getAbsoluteFile() + "/version.sh");

        if (!script.exists()) {
            return "not exists file";
        }

        String[] command = new String[]{"sh", script.getAbsolutePath()};

        try {
            StringBuilder result = this.executeCommand(command);
            return result.toString();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return "";
    }

    public String getBranch() {
        File sourceDir = new File(config.getSourceDirPath());
        String[] command = new String[]{"cd " + sourceDir.getAbsolutePath().replaceAll("\\\\", "/"), ";", "git branch"};
        try {
            StringBuilder result = this.executeCommand(command);
            return result.toString();
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }


        return "a";
    }

    private StringBuilder executeCommand(final String[] command) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);

        Process process = processBuilder.start();

        StringBuilder result = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream(), "MS949"))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                result.append(line);
                line = bufferedReader.readLine();
            }
            process.waitFor();
        } catch (InterruptedException e) {
            log.warn(e.getMessage());
        }
        process.destroy();
        return result;
    }
}
