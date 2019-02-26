package com.jerry.parse;

import com.jerry.model.CommonCode;
import com.jerry.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
@Service
public class LogParser {
	private final static String PRE_ERROR_LOGS = "\tat";

	/**
	 * Read Log File
	 */
	public String read(final File file) {
		if (file == null || !file.exists()) {
			return CommonCode.NOT_EXIST_FILE.getMessage();
		}

		return "";
	}

	/**
	 * get Error Logs
	 * @param file
	 * @return
	 */
	public LogModel parse(final File file) {
		if (file == null || !file.exists()) {
			return new LogModel(CommonCode.FAIL);
		}

		LogModel logModel = new LogModel();

		StringBuilder builder = new StringBuilder();
		String line;
		boolean isCatchException = false;
		try (BufferedReader reader = new BufferedReader(new FileReader(file))){
			int index = 0;
			String header = "";
			while ((line = reader.readLine()) != null) {
				if (!isCatchException && !line.contains(PRE_ERROR_LOGS)) {
					header = line;
					continue;
				}

				isCatchException = true;
				if ("".equals(line) || !line.contains(PRE_ERROR_LOGS)) {
					isCatchException = false;
					index = 0;

					logModel.setStackTraces(builder);
					builder.delete(0, builder.length());
					continue;
				}

				if (index == 0) {
					logModel.setExceptions(header);
					index++;
				}

				builder.append(line).append("\n");
				index++;
			}
			if (builder.length() > 0) {
				logModel.setStackTraces(builder);
			}
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}

		return logModel;
	}
}
