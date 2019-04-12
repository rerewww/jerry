package com.jerry.system;

import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by son on 2019-04-12.
 */
@Primary
@Service
public class SystemServiceFactoryBean {
	@Autowired WindowSystemService windowSystemService;
	@Autowired LinuxSystemService linuxSystemService;

	public SystemService getObject() {
		if (System.getProperty("os.name").toLowerCase().contains("window")) {
			return windowSystemService;
		} else {
			return linuxSystemService;
		}
	}
}