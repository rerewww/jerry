package com.jerry.parse;

import com.jerry.model.CommonCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
@Service
public class LogParser {
	private static final int BUFFER_SIZE = 4096;
	public String parse(final File file) {
		if (!file.exists()) {
			return CommonCode.FAIL.getMessage();
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			int read = 0;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((read = reader.read(buffer)) >= 0) {
			}
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

		return file.getName();
	}
}
