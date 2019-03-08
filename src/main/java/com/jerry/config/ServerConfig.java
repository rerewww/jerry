package com.jerry.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by son on 2019-02-27.
 */
@NoArgsConstructor
@Service
public class ServerConfig {

	/**
	 * It for detect source dir path
	 */
	@Value("${source.dir.path}")
	@Getter
	private String sourceDirPath;

	/**
	 * It for detect log file path
	 */
	@Value("${log.file.path}")
	@Getter
	private String logFilePath;

	/**
	 * It for highlighting stacktraces
	 */
	@Value("${source.package.prefix}")
	@Getter
	private String sourcePackagePrefix;
}