package com.jerry.controller;

import com.jerry.parse.LogManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by son on 2019-03-05.
 */
@Slf4j
@Component
public class TailLogScheduler {
    @Autowired LogManager logManager;
    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(cron="*/2 * * * * *")
    public void start() {
        try {
	        List<String> logs = logManager.getTomcatLogs();
            int beforeSize = logs.size();
            template.setMessageConverter(new StringMessageConverter());
            template.convertAndSend("/subscribe/notice", StringUtils.collectionToCommaDelimitedString(logs));

            if (beforeSize == logs.size()) {
                logManager.clearLogs();
            }
        } catch (Exception e) {
	        log.warn(e.getMessage(), e);
        }
    }
}
