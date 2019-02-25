package com.jerry.parse;

import com.jerry.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static org.assertj.core.api.Assertions.assertThat;

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
		Files.copy(new File("src/test/resources/test.log").toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
		LogModel logModel = parser.parse(file);
	}
}