package com.jerry.parse;

import org.junit.Test;

import java.io.File;

public class TailerTest {
	private Tailer tailer = new Tailer();

	@Test
	public void tailTest() {
		File srcFile = new File("D:/backup/test.log");
		tailer.setSrcFile(srcFile);

		Thread thread = new Thread(tailer);
		thread.run();
		tailer.stop();
	}
}
