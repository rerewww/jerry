package com.jerry.parse;

import com.jerry.model.CommonCode;
import com.jerry.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
@Service
public class LogParser {
	private final static Set<String> PRE_ERROR_LOGS = new HashSet<>(Arrays.asList("An error occurred", "Error processing request"));
	public LogModel parse(final File file) {
		if (!file.exists()) {
			return new LogModel(CommonCode.FAIL);
		}

		LogModel logModel = new LogModel();

		StringBuilder builder = new StringBuilder();
		String line;
		boolean isCatchException = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			int index = 0;
			while ((line = reader.readLine()) != null) {

				if (!isCatchException && !isException(line)) {
					continue;
				}

				isCatchException = true;
				if ("".equals(line)) {
					isCatchException = false;
					index = 0;

					logModel.setStackTraces(builder);
					builder.delete(0, builder.length());
					continue;
				}

				if (index == 0) {
					index++;
					continue;
				}

				if (index == 1) {
					index++;
					logModel.setExceptions(line);
					continue;
				}

				builder.append(line).append("\n");
				index++;
			}
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

		return logModel;
	}

	private boolean isException(final String line) {
		boolean result = false;
		for (String exception : PRE_ERROR_LOGS) {
			if (line.contains(exception)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
