package com.jerry.service;

import com.jerry.model.LogModel;
import com.jerry.parse.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by son on 2019-02-26.
 */
@Service
public class LogService {
	private LogManager logManager;

	@Autowired
	LogService(final LogManager logManager) {
		this.logManager = logManager;
	}

	public String read() {
		return "";
	}

	public LogModel parse() {
		File file = logManager.getLogFile();
		return logManager.parse(file);
	}
}
