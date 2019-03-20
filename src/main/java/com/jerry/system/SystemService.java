package com.jerry.system;

import com.jerry.config.ServerConfig;
import com.sun.management.OperatingSystemMXBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public Map<String, String> getInfos() {
        Pattern pattern = Pattern.compile("Servernumber:([0-9].*)");
        Map<String, String> result = new HashMap<>();

        String version = getTomcatVersion().replaceAll(" ", "");
        Matcher matcher = pattern.matcher(version);

        result.put("version", "no exist");
        if (matcher.find()) {
            result.put("version", matcher.group(1));
        }

        String branch = getBranchInfo();
        result.put("branch", StringUtils.isEmpty(branch) ? "no exist" : branch);
        return result;
    }

    private String getTomcatVersion() {
        if (System.getProperty("os.name").toLowerCase().contains("window")) {
            return "Do not windows";
        }

        File tomcatDir = new File(config.getLogFilePath()).getParentFile().getParentFile();
        File versionScript = new File(String.format("%s/bin/version.sh", tomcatDir.getAbsolutePath()));

        List<String> command = new ArrayList<>();
        command.add("/bin/bash");
        command.add("-c");
        command.add(versionScript.getAbsolutePath());

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectInput();

        try {
            Process process = processBuilder.start();
            String result = StreamUtils.copyToString(process.getInputStream(), StandardCharsets.UTF_8);
            process.destroy();

            return result;
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return "";
    }

     public String getBranchInfo() {
        if (System.getProperty("os.name").toLowerCase().contains("window")) {
            return "Do not windows";
        }

        File projectDir = new File(config.getSourceDirPath());

        List<String> command = new ArrayList<>();
        command.add("cd");
        command.add(projectDir.getAbsolutePath());
        command.add(";");
        command.add("git");
        command.add("branch");

        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectInput();

        try {
            Process process = processBuilder.start();
            String result = StreamUtils.copyToString(process.getInputStream(), StandardCharsets.UTF_8);
            process.destroy();

            return result;
        } catch (IOException e) {
            log.warn(e.getMessage(), e);
        }

        return "";
    }
}
