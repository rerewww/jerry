package com.jerry.controller;

import com.jerry.model.CommonCode;
import com.jerry.model.LogModel;
import com.jerry.parse.LogManager;
import com.jerry.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by son on 2019-03-05.
 */
@Slf4j
@Component
public class TailLogScheduler {
	@Autowired LogManager logManager;
	@Autowired LogService logService;
	private SimpMessagingTemplate template;

	@Autowired
	public void setTemplate(SimpMessagingTemplate template) {
		this.template = template;
	}

	@Scheduled(cron="*/1 * * * * *")
	public void start() {
		try {
			List<String> logs = logManager.getTomcatLogs();
			int beforeSize = logs.size();
			template.setMessageConverter(new StringMessageConverter());
			template.convertAndSend("/subscribe/logs", StringUtils.collectionToDelimitedString(logs, "\r\t"));

			if (beforeSize == logs.size()) {
				logManager.clearLogs();
			}
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}

	@Scheduled(cron="*/1 * * * * *")
	public void startAccessLogs() {
		try {
			LogModel model = logManager.getAccessLogs();
			if (CommonCode.NO_CHANGE_LOGS == model.getCommonCode()) {
				return;
			}
			template.setMessageConverter(new MappingJackson2MessageConverter());
			template.convertAndSend("/subscribe/accessLogs", model);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}

	@Scheduled(cron="*/1 * * * * *")
	public void startErrorLogs() {
		try {
			LogModel model = logService.parse();
			if (CommonCode.NO_CHANGE_LOGS == model.getCommonCode()) {
				return;
			}
			template.setMessageConverter(new MappingJackson2MessageConverter());
			template.convertAndSend("/subscribe/errorLogs", model);

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}

	@Scheduled(cron="*/10 * * * * *")
	public void startUsage() {
		try {
			Map<String, Integer> usage = logService.getUsage();
			template.setMessageConverter(new MappingJackson2MessageConverter());
			template.convertAndSend("/subscribe/usage", usage);

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
	}
}
