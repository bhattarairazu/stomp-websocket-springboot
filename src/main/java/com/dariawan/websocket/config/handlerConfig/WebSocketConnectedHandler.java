/**
 * Spring Boot + WebSocket Examples  (https://www.dariawan.com)
 * Copyright (C) 2020 Dariawan <hello@dariawan.com>
 *
 * Creative Commons Attribution-ShareAlike 4.0 International License
 *
 * Under this license, you are free to:
 * # Share - copy and redistribute the material in any medium or format
 * # Adapt - remix, transform, and build upon the material for any purpose,
 *   even commercially.
 *
 * The licensor cannot revoke these freedoms
 * as long as you follow the license terms.
 *
 * License terms:
 * # Attribution - You must give appropriate credit, provide a link to the
 *   license, and indicate if changes were made. You may do so in any
 *   reasonable manner, but not in any way that suggests the licensor
 *   endorses you or your use.
 * # ShareAlike - If you remix, transform, or build upon the material, you must
 *   distribute your contributions under the same license as the original.
 * # No additional restrictions - You may not apply legal terms or
 *   technological measures that legally restrict others from doing anything the
 *   license permits.
 *
 * Notices:
 * # You do not have to comply with the license for elements of the material in
 *   the public domain or where your use is permitted by an applicable exception
 *   or limitation.
 * # No warranties are given. The license may not give you all of
 *   the permissions necessary for your intended use. For example, other rights
 *   such as publicity, privacy, or moral rights may limit how you use
 *   the material.
 *
 * You may obtain a copy of the License at
 *   https://creativecommons.org/licenses/by-sa/4.0/
 *   https://creativecommons.org/licenses/by-sa/4.0/legalcode
 */
package com.dariawan.websocket.config.handlerConfig;

import com.dariawan.websocket.model.WebSocketSession;
import com.dariawan.websocket.model.WebSocketSessionManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

import static java.util.Optional.ofNullable;

@Slf4j
public class WebSocketConnectedHandler<S> implements ApplicationListener<SessionConnectedEvent> {

    @Autowired
    private WebSocketSessionManager webSocketSessionManager;
    public WebSocketConnectedHandler(SimpMessageSendingOperations messagingTemplate) {
        super();
    }
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        WebSocketSession session = (WebSocketSession) event.getMessage().getHeaders().get("simpSessionId");
//        sessionManager.saveWebSocketSession(session.getId(), session);
//    }
    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
//        event.
        ofNullable(readUser(event)).ifPresent(user -> {
            String sessionId = log(event, user);
            webSocketSessionManager.saveWebSocketSession(sessionId,getWebSocketSession(event));

        });
    }
    private String log(SessionConnectedEvent event, Principal user) {
        String sessionId = readSessionId(event);
        log.info("User {} connected to session id {}", user.getName(), sessionId);
        return sessionId;
    }

    private WebSocketSession getWebSocketSession(SessionConnectedEvent event){
       return (WebSocketSession) event.getMessage().getHeaders().get("simpSessionId");
    }

    String readSessionId(SessionConnectedEvent event) {
        return SimpMessageHeaderAccessor.getSessionId(event.getMessage().getHeaders());
    }

    Principal readUser(SessionConnectedEvent event) {
        MessageHeaders headers = event.getMessage().getHeaders();
        return SimpMessageHeaderAccessor.getUser(headers);
    }
}
