package vk.config.webSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import vk.service.UserService;

public class StompDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {
    @Autowired
    private UserService userService;
    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        StompHeaderAccessor sha = StompHeaderAccessor.wrap(event.getMessage());
        userService.editLastOnline(sha.getSessionId());
    }
}
