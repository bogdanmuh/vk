package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.ChatRequest;
import vk.controller.pojo.ChatResponse;
import vk.domain.Message;
import vk.domain.User;
import vk.repos.MessageRepository;
import vk.repos.UserRepository;

import java.util.Date;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ChatController {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/chat")
    public ResponseEntity<?> saveMessage(@RequestBody ChatRequest chatRequest) {
        User from = userRepository.findByUsername(chatRequest.getFrom()).get();
        User to = userRepository.findByUsername(chatRequest.getTo()).get();

        Message message = new Message();
        message.setSender(from);
        message.setRecipient(to);
        message.setMessage(chatRequest.getMessage());
        message.setDate(new Date());

        messageRepository.save(message);
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/chat")
    public ResponseEntity<?> getMessage(@RequestParam String from, @RequestParam String to) {
        return ResponseEntity.ok(messageRepository.getMessage(from, to).stream()
                .map(x -> new ChatResponse(
                        x.getSender().getUsername(),
                        x.getRecipient().getUsername(),
                        x.getDate(),
                        x.getMessage()))
                .collect(Collectors.toList()));
    }

    @PostMapping("/chat/update")
    public ResponseEntity<?> getMessage(@RequestBody ChatRequest chatRequest) {
        return ResponseEntity.ok(messageRepository.getMessage(
                chatRequest.getFrom(),
                chatRequest.getTo(),
                chatRequest.getDate()));
    }
}
