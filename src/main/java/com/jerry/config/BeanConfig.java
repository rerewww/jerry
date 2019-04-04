package com.jerry.config;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by son on 2019-04-04.
 */
@Configuration
public class BeanConfig {
	@Autowired ServerConfig serverConfig;

	@Bean
	public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
		return server -> {
			if (server instanceof TomcatServletWebServerFactory) {
				((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(redirectConnector());
			}
		};
	}

	private Connector redirectConnector() {
		Connector connector = new Connector(serverConfig.getAjpProtocol());
		connector.setScheme("http");
		connector.setPort(serverConfig.getAjpPort());
		connector.setSecure(false);
		connector.setAllowTrace(false);
		return connector;
	}
}
