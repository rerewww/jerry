package com.jerry.handler;

import com.jerry.config.ServerConfig;
import com.jerry.parse.LogManager;
import com.jerry.service.LogService;
import com.jerry.system.SystemServiceFactoryBean;
import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Created by son on 2019-02-27.
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired ServerConfig config;
    @Autowired LogManager logManager;
    @Autowired SystemServiceFactoryBean systemServiceFactoryBean;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new ProjectFileArgumentResolver(config));
        resolvers.add(new UserInfoArgumentResolver(config));
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
        return server -> {
            if (TomcatServletWebServerFactory.class.isInstance(server)) {
                server.addAdditionalTomcatConnectors(redirectConnector());
            }
        };
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(config.getAjpProtocol());
        connector.setPort(config.getAjpPort());
        connector.setSecure(false);
        connector.setAllowTrace(false);
        return connector;
    }

    @Bean
    public LogService logService() {
        return new LogService(logManager, systemServiceFactoryBean.getObject());
    }
}
