package com.jerry.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by son on 2019-02-26.
 */
@AllArgsConstructor
public class ResultModel {

	@Getter
	private boolean success;
	@Getter
	private String message;
	@Getter
	private LogModel logModel;
}
