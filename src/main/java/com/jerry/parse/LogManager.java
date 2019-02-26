package com.jerry.parse;

import com.jerry.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by son on 2019-02-26.
 */
@Service
public class LogManager implements Manager {
	private LogParser logParser;

	@Autowired
	LogManager(final LogParser logParser) {
		this.logParser = logParser;
	}

	public LogModel parse(final File file) {
		return logParser.parse(file);
	}

	@Override
	public File getLogFile() {
		return new File("D:\\backup\\test.log");
	}
}
