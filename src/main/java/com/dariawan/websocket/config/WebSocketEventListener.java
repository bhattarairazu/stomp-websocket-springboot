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
package com.dariawan.websocket.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Component
@Slf4j
public class WebSocketEventListener {
//    Events and Interception
//    Several ApplicationContext events (listed below) are published and can be received by
//    implementing Spring’s ApplicationListener interface.
//
//    BrokerAvailabilityEvent —
//     indicates when the broker becomes available/unavailable.
//    While the "simple" broker becomes available immediately on startup and remains so while the application is running,
//    the STOMP "broker relay" may lose its connection to the full featured broker,
//    for example if the broker is restarted. The broker relay has reconnect logic and will re-establish the "system"
//    connection to the broker when it comes back, hence this event is published whenever the state changes from
//    connected to disconnected and vice versa. Components using the SimpMessagingTemplate should subscribe to this event
//    and avoid sending messages at times when the broker is not available. In any case they should be prepared to
//    handle MessageDeliveryException when sending a message.

//            SessionConnectEvent —
//             published when a new STOMP CONNECT is received indicating the start of a new client session.
//            The event contains the message representing the connect including the session id, user information (if any),
//            and any custom headers the client may have sent. This is useful for tracking client sessions.
//            Components subscribed to this event can wrap the contained message using SimpMessageHeaderAccessor or StompMessageHeaderAccessor.

//    SessionConnectedEvent —
//     published shortly after a SessionConnectEvent when the broker has sent a STOMP CONNECTED frame in response to the CONNECT.
//    At this point the STOMP session can be considered fully established.

//            SessionSubscribeEvent —
//             published when a new STOMP SUBSCRIBE is received.

//    SessionUnsubscribeEvent —
//     published when a new STOMP UNSUBSCRIBE is received.

//    SessionDisconnectEvent —
//     published when a STOMP session ends. The DISCONNECT may have been sent from the client, or it may also be automatically generated when the WebSocket session is closed. In some cases this event may be published more than once per session. Components should be idempotent with regard to multiple disconnect events.
    @EventListener
    public void handleWebSocketConnectedListneer(SessionConnectedEvent connectedEvent){
        log.info("Session is connected: {}", connectedEvent.getMessage());
    }

    @EventListener
    public void handleWebSocketDisconnectListner(SessionDisconnectEvent event){
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        log.info("Disconnect event : {}", headerAccessor.getSessionId());
        String username = (String)headerAccessor.getSessionAttributes().get("simpSessionId");
        log.info("Username : {}", username);
    }

    @EventListener
    public void handleSessionConnectListener(SessionConnectEvent sessionConnectEvent){
        log.info("Session is in connect state: {}", sessionConnectEvent.getUser());
    }

    @EventListener
    public void handleSessionSubscribeEvent(SessionSubscribeEvent sessionSubscribeEvent) {
        GenericMessage message = (GenericMessage) sessionSubscribeEvent.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");
        log.info("User is subscribed to : {}", simpDestination);
    }

    @EventListener
    public void handleUnsubscribeEvent(SessionUnsubscribeEvent unsubscribeEvent) {
        GenericMessage message = (GenericMessage) unsubscribeEvent.getMessage();
        String simpDestination = (String) message.getHeaders().get("simpDestination");
        log.info("Session unsubscribe event: {}", simpDestination);
    }


}
