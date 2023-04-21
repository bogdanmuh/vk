package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.ChatRequest;
import vk.service.MessageService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ChatController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/chat")
    public ResponseEntity<?> saveMessage(@RequestBody ChatRequest chatRequest) {
        messageService.saveMessage(chatRequest);
        return ResponseEntity.ok("ok");
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
