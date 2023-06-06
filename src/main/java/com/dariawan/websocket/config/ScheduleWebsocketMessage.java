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


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleWebsocketMessage {
    private final SimpMessagingTemplate template;

   // @Scheduled(fixedDelay = 2000)
    @Async
    public void trigger(){
        log.info("Schedule sending....");
        template.convertAndSend("/topic/test1",createOBject());
        template.convertAndSend("/topic/test2",createOBject());
        template.convertAndSend("/topic/test3",createOBject());
        template.convertAndSend("/topic/test4",createOBject());
        template.convertAndSend("/topic/test5",createOBject());
        template.convertAndSend("/topic/test6",createOBject());
    }
    private InfoTestObject createOBject(){
        InfoTestObject object = new InfoTestObject();
        object.setName("Test");
        object.setValue(1);
        object.setDateTime(LocalDateTime.now());
        return object;
    }
    @Getter
    @Setter
    public class InfoTestObject {
        private String name;
        private Integer value;
        private LocalDateTime dateTime;
    }


}
