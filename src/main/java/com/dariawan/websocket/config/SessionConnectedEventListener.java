package com.dariawan.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

//@Component
@Slf4j
public class SessionConnectedEventListener implements ApplicationListener<SessionDisconnectEvent>{

    @Override
    public void onApplicationEvent(SessionDisconnectEvent sessionSubscribeEvent) {
        log.info("Subscribe event : disconnect: {}", sessionSubscribeEvent.getMessage());
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(sessionSubscribeEvent.getMessage());
        log.info("Logged in user: {}",headerAccessor.getUser());
        log.info("Session ID: {}", headerAccessor.getSessionId());
    }
}
