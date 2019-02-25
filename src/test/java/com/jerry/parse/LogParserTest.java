package com.jerry.parse;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
public class LogParserTest {
	private LogParser parser = new LogParser();
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Before
	public void setUp() throws IOException {
		folder.create();
	}

	@Test
	public void parseTest() throws IOException {
		File file = new File("D:/backup/catalina.2019-02-15.log");
		String result = parser.parse(file);

		log.info(result);
	}
}