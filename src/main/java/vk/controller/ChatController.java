package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.ChatRequest;
import vk.controller.pojo.ChatResponse;
import vk.service.MessageService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ChatController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/topic")
    public void processMessage(@Payload ChatRequest chatMessage) {
        messageService.saveMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getTo(),
                "/queue/messages",
                    new ChatResponse(
                            chatMessage.getFrom(),
                            chatMessage.getTo(),
                            chatMessage.getDate(),
                            chatMessage.getMessage(),
                            "message"));
    }

    @GetMapping("/chat")
    public ResponseEntity<?> getMessage(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(messageService.getMessage(from,to));
    }

    @PostMapping("/chat/update")
    public ResponseEntity<?> getMessage(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(messageService.getMessage(chatRequest));
    }
}
