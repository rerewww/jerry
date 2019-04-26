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
				if (m.getName().indexOf("set") != 0 && m.getName().toLowerCase().substring(3, m.getName().length()).contains(f.getName().toLowerCase())) {
					try {
						result.put(f.getName(), m.invoke(config));
						break;
					} catch (InvocationTargetException | IllegalAccessException e) {
						log.warn(e.getMessage(), e);
					}
				}
			}
		}
		return result;
	}

	/**
	 * set enviornment
	 */
	public void setEnviornments(final Map<String, String> data) {
		for (Map.Entry<String, String> item : data.entrySet()) {
			for (Method m : config.getClass().getDeclaredMethods()) {
				if (m.getName().contains("get")
						|| !m.getName().toLowerCase().substring(3, m.getName().length()).equals(item.getKey().toLowerCase())) {
					continue;
				}

				try {
					m.invoke(config, item.getValue());
				} catch (IllegalAccessException | InvocationTargetException e) {
					log.warn(e.getMessage(), e);
				}
			}
		}
	}
}
