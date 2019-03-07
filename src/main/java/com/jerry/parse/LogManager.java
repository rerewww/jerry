package com.jerry.parse;

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
	private Tailer tailer;
	private static final String LOG_FILE_PATH = "D:/backup/test.log";

	@Autowired
	LogManager(final LogParser parser, final Tailer tailer) {
		this.parser = parser;

		File srcFile = new File(LOG_FILE_PATH);
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

	@Override
	public File getLogFile() {
		return new File(LOG_FILE_PATH);
	}
}
