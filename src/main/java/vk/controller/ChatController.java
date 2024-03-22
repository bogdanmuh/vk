package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.ChatRequest;
import vk.controller.pojo.ChatResponse;
import vk.controller.pojo.NewChatRequest;
import vk.domain.Chats;
import vk.service.ChatsService;
import vk.service.MessageService;

import java.util.Date;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@AllArgsConstructor
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatsService chatsService;

    @MessageMapping("/topic")
    public void processMessage(@Payload ChatRequest chatMessage) {
        messageService.saveMessage(chatMessage);
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(),
                "/queue/messages",
                    new ChatResponse(
                            chatMessage.getSender(),
                            chatMessage.getDate(),
                            chatMessage.getMessage(),
                            "message"));

    }

    @GetMapping(value = "/chat", params = { "id" })
    public ResponseEntity<?> getMessage(@RequestParam Long id) {
        return ResponseEntity.ok(messageService.getMessage(id));
    }

    @GetMapping(value = "/chat", params = { "usernames" })
    public ResponseEntity<?> getMessage(@RequestParam String [] usernames) {
        return ResponseEntity.ok(messageService.getMessage(usernames));
    }

    @PostMapping("/chat/new")
    public ResponseEntity<?> newChat(@RequestBody NewChatRequest newChatRequest) {
        return ResponseEntity.ok(chatsService.save(newChatRequest));
    }

    @PostMapping("/chat/update")
    public ResponseEntity<?> getMessage(@RequestParam Long id, @RequestParam Date date) {
        return ResponseEntity.ok(messageService.getMessage(id, date));
    }

}
