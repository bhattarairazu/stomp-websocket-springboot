package com.dariawan.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    @EventListener
    public void handleWebSocketConnectListneer(SessionConnectedEvent connectedEvent){
        log.info("Session is connected: {}", connectedEvent.getMessage());
    }

    @EventListener
    public void handleWebSocketDisconnectListner(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Disconnect event : {}", headerAccessor.getSessionAttributes());
        String username = (String)headerAccessor.getSessionAttributes().get("username");
        log.info("Username : {}", username);
    }

}
