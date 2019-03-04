package com.jerry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by son on 2019-03-05.
 */
@Component
public class TailLogScheduler {

    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Scheduled(cron="*/5 * * * * *")
    public void start() {
        try {
            template.setMessageConverter(new StringMessageConverter());
            template.convertAndSend("/subscribe/notice", new Date().toString() + " : 서버 메시지");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
