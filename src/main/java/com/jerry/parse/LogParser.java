package com.jerry.parse;

import com.jerry.model.CommonCode;
import com.jerry.model.LogModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by son on 2019-02-25.
 */
@Slf4j
@Service
public class LogParser {
	private final static String PRE_ERROR_LOGS = "\tat";
	private long fileLength;

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

		if (fileLength == file.length()) {
			return new LogModel(CommonCode.NO_CHAGE_LOGS);
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

				if (line.contains("\tat ")) {
					line = line.substring(4);
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

		fileLength = file.length();
		return logModel;
	}

	/**
	 * get src file contents
	 */
	public List<String> getViewCode(final File srcFile, final int line, final int range) {
		List<String> contents = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader(srcFile))) {
			String readLine = "";
			int start = line - range;
			int end = line + range;
			int index = 1;

			while ((readLine = reader.readLine()) != null) {
				if (index < start) {
					index++;
					continue;
				}

				if (index > end) {
					break;
				}
				String code = index + ":" + readLine.replaceAll("\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
				contents.add(code);
				index++;
			}
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
		}
		return contents;
	}
}
