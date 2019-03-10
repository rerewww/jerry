package com.jerry.parse;

import com.jerry.config.ServerConfig;
import com.jerry.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.List;

/**
 * Created by son on 2019-02-26.
 */
@Service
public class LogManager implements Manager {
	private LogParser parser;
	private ServerConfig config;
	private Tailer tailer;

	@Autowired
	LogManager(final LogParser parser, final Tailer tailer, final ServerConfig config) {
		this.parser = parser;
		this.config = config;

		File srcFile = new File(config.getLogFilePath());
		tailer.setSrcFile(srcFile);
		tailer.setPointer(srcFile.length());
		this.tailer = tailer;
	}

	@PostConstruct
	public void runTailLog() {
		new Thread(tailer).start();
	}

	public void clearLogs() {
		tailer.clear();
	}

	public List<String> getTomcatLogs() {
		List<String> logs = tailer.getLogs();
		return logs;
	}

	public LogModel parse(final File file) {
		return parser.parse(file);
	}

	public List<String> getViewCode(final File file, final int line, final int range) {
		List<String> contents = parser.getViewCode(file, line, range);
		return contents;
	}

	public LogModel getAccessLogs() {
		File accessLogFile = new File(config.getAccessLogFilePath());
		return parser.getAccessLogs(accessLogFile);
	}

	@Override
	public File getLogFile() {
		return new File(config.getLogFilePath());
	}
}
