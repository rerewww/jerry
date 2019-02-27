package com.jerry.parse;

import com.jerry.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by son on 2019-02-26.
 */
@Service
public class LogManager implements Manager {
	private LogParser parser;

	@Autowired
	LogManager(final LogParser parser) {
		this.parser = parser;
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
		return new File("D:\\backup\\test.log");
	}
}
