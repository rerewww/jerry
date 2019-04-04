package com.jerry.model;

import lombok.AllArgsConstructor;

/**
 * Created by son on 2019-02-25.
 */
@AllArgsConstructor
public enum CommonCode {
	SUCCESS(1, "success"), FAIL(0, "fail"), NOT_EXIST_FILE(-1, "not_exist_file"), NO_CHANGE_LOGS(2, "no_change_logs");

	private int code;
	private String message;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
