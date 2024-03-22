package vk.config.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import vk.service.UserService;

public class StompConnectEvent implements ApplicationListener<SessionConnectEvent> {

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        userService.addUser(sha.getSessionId(),sha.getNativeHeader("username").get(0));
    }

}