package com.jerry.service;

import com.jerry.config.ServerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by son on 2019-04-07.
 */
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
    public Map<String, String> getServerEnviornments() {
        Map<String, String> result = new HashMap<>();
        return result;
    }
}
