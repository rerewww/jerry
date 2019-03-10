package com.jerry.service;

import com.jerry.model.LogModel;
import com.jerry.parse.LogManager;
import com.jerry.project.ProjectFile;
import com.jerry.system.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by son on 2019-02-26.
 */
@Service
public class LogService {
	private LogManager logManager;
	private SystemService systemService;

	@Autowired
	LogService(final LogManager logManager, final SystemService systemService) {
		this.logManager = logManager;
		this.systemService = systemService;
	}

	public LogModel parse() {
		File file = logManager.getLogFile();
		return logManager.parse(file);
	}

	public List<String> getViewCode(final ProjectFile projectFile, final int line, final int range) {
		List<String> contents = logManager.getViewCode(new File(projectFile.getFilePath()), line, range);
		return contents;
	}

	public List<String> getTomcatLogs() {
		return logManager.getTomcatLogs();
	}

	public void start() {
		logManager.runTailLog();
	}

	public Map<String, Integer> getUsage() {
		Map<String, Integer> result = systemService.getUsage();
		return result;
	}

	public LogModel getAccessLogs() {
		return logManager.getAccessLogs();
	}
}
