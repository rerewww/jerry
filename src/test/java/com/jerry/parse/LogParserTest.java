package com.jerry.parse;

import com.jerry.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

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
		File file = folder.newFile();
		Files.copy(new File("D:/backup/log.txt").toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		LogModel logModel = parser.parse(file);

		assertThat(logModel.getExceptions().size(), is(3));
		assertThat(logModel.getStackTraces().size(), is(3));
	}

	@Test
	public void readTest() {
		String result = parser.read(null);
		assertThat(result, is("not_exist_file"));
	}

	@Test
	public void getViewCodeTest() {
		File srcFile = new File("D:/backup/log.txt");
		List<String> result = parser.getViewCode(srcFile, 13, 5);

		for (String str : result) {
			log.info(str);
		}
	}

	@Test
	public void getAccessLogsTest() {
		File srcFile = new File("D:/backup/access_log.txt");
		LogModel model = parser.getAccessLogs(srcFile);
	}
}