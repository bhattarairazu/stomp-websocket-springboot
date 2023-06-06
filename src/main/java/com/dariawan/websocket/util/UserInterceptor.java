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
package com.dariawan.websocket.util;

import java.util.LinkedList;
import java.util.Map;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Service;

@Service
public class UserInterceptor implements ChannelInterceptor {
    static final String API_KEY_HEADER = "authKey";
    static final String SESSION_KEY_HEADER = "simpSessionId";
    static final String WS_ID_HEADER = "ws-id";

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor
                = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            Object raw = message
                    .getHeaders()
                    .get(SimpMessageHeaderAccessor.NATIVE_HEADERS);

            if (raw instanceof Map) {
                Object name = ((Map) raw).get("username");

                if (name instanceof LinkedList) {
                    accessor.setUser(new User(((LinkedList) name).get(0).toString()));
                }
            }
        }
        return message;
    }

    ///Second way for authentication user token.
    //it can be used in webscoket with spring security
    /**
     * Processes a message before sending it
     */
//    @Override
//    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        final StompHeaderAccessor accessor = readHeaderAccessor(message);
//
//        if (accessor.getCommand() == StompCommand.CONNECT) {
//
//            String wsId = readWebSocketIdHeader(accessor);
//            String apiKey = readAuthKeyHeader(accessor);
//            String sessionId = readSessionId(accessor);
//
//            // authenticate the user and if that's successful add their user information to the headers.
//            UsernamePasswordAuthenticationToken user = new UsernamePasswordAuthenticationToken(wsId, null);
//            accessor.setUser(user);
//            accessor.setHeader("connection-time", LocalDateTime.now().toString());
//            log.info("User with authKey '{}', ws-id {} session {} make a WebSocket connection and generated the user {}", apiKey, wsId, sessionId, user.toString());
//
//        }
//
//        return message;
//
//    }

    /**
     * Instantiate an object for retrieving the STOMP headers
     */
//    private StompHeaderAccessor readHeaderAccessor(Message<?> message) {
//        final StompHeaderAccessor accessor = getAccessor(message);
//        if (accessor == null) {
//            throw new AuthenticationCredentialsNotFoundException("Fail to read headers.");
//        }
//        return accessor;
//    }
//
//    private String readSessionId(StompHeaderAccessor accessor) {
//        return ofNullable(accessor.getMessageHeaders().get(SESSION_KEY_HEADER))
//                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Session header not found")).toString();
//    }
//
//    private String readAuthKeyHeader(StompHeaderAccessor accessor) {
//        final String authKey = accessor.getFirstNativeHeader(API_KEY_HEADER);
//        if (authKey == null || authKey.trim().isEmpty())
//            throw new AuthenticationCredentialsNotFoundException("Auth Key Not Found");
//        return authKey;
//    }
//
//    private String readWebSocketIdHeader(StompHeaderAccessor accessor) {
//        final String wsId = accessor.getFirstNativeHeader(WS_ID_HEADER);
//        if (wsId == null || wsId.trim().isEmpty())
//            throw new AuthenticationCredentialsNotFoundException("Web Socket ID Header not found");
//        return wsId;
//    }
//
//    StompHeaderAccessor getAccessor(Message<?> message) {
//        return MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//    }

}