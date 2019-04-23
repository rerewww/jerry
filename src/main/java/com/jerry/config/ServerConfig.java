package com.jerry.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
	@Getter @Setter
	private String sourceDirPath;

	/**
	 * It for detect log file path
	 */
	@Value("${log.file.path}")
	@Getter @Setter
	private String logFilePath;

	/**
	 * It for highlighting stacktraces
	 */
	@Value("${source.package.prefix}")
	@Getter @Setter
	private String sourcePackagePrefix;

	/**
	 * It for detect access log file path
	 */
	@Value("${accesslog.file.path}")
	@Getter @Setter
	private String accessLogFilePath;

	/**
	 * It for ajp protocol
	 */
	@Value("${ajp.protocol}")
	@Getter
	private String ajpProtocol;

	/**
	 * It for ajp port
	 */
	@Value("${ajp.port}")
	@Getter
	private int ajpPort;

	/**
	 * It for ajp protocol
	 */
	@Value("${ajp.enabled}")
	@Getter
	private boolean ajpEnabled;

	/**
	 * It for Admin Id
	 */
	@Value("${admin.user.id}")
	@Getter
	private String adminUserId;

	/**
	 * It for Admin Password
	 */
	@Value("${admin.user.password}")
	@Getter
	private String adminUserPassword;
}