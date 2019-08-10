package com.jerry.handler;

import com.jerry.config.ServerConfig;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.io.IOException;

/**
 * Created by son on 2019-08-09.
 */
public class ProjectFileArgumentResolverTest {
	@Rule public TemporaryFolder folder = new TemporaryFolder();
	private ProjectFileArgumentResolver projectFileArgumentResolver;
	@Mock private ServerConfig serverConfig;

	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);
		projectFileArgumentResolver = new ProjectFileArgumentResolver(serverConfig);
	}

	@Test
	public void getJavaFileFromFileName() throws IOException {
		File testSrcDir = folder.newFolder("src");
		File testFile = folder.newFile("test.java");
		FileUtils.moveFileToDirectory(testFile, testSrcDir, false);

		File result = projectFileArgumentResolver.getJavaFileFromFileName(folder.getRoot().listFiles(),
				testFile.getName().substring(0, testFile.getName().indexOf(".")));
	}
}