package com.jerry.service;

import com.jerry.config.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by son on 2019-04-07.
 */
@Slf4j
@Service
public class AuthService {
    private ServerConfig config;

    @Autowired
    AuthService(final ServerConfig config) {
        this.config = config;
    }

    public boolean checkAuth(final String userId, final String pw) {
        return userId.equals(config.getAdminUserId()) && pw.equals(config.getAdminUserPassword());
    }

    public boolean checkSession(final String userId, final String uniqueKey) {
        return StringUtils.hasText(uniqueKey) && uniqueKey.contains(userId);
    }

    public String createUniqueKey(final String userId) {
        return String.format("%s_%s", userId, UUID.randomUUID().toString());
    }

    /**
     * Read a application.properties file
     * @return Map
     */
    public Map<String, Object> getServerEnviornments() {
        Map<String, Object> result = new HashMap<>();
	    for (Field f : config.getClass().getDeclaredFields()) {
            for (Method m : config.getClass().getDeclaredMethods()) {
	            if (!m.getName().toLowerCase().contains(f.getName().toLowerCase())) {
		            continue;
                }
                try {
                    result.put(f.getName(), m.invoke(config));
                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return result;
    }
}
