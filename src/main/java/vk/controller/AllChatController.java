package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.pojo.AllChatResponse;
import vk.repos.MessageRepository;

import java.util.stream.Collectors;


@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class AllChatController {
    @Autowired
    private MessageRepository messageRepository;
    @GetMapping("/allChat")
    public ResponseEntity<?> getCompanions(@RequestParam String to) {
        return ResponseEntity.ok(messageRepository.findDistinctTop20ByRecipient(to).stream()
                .map(x -> new AllChatResponse(
                        x.getSender().getUsername(),
                        x.getSender().getFirstName(),
                        x.getSender().getLastName(),
                        x.getDate(),
                        x.getMessage()))
                .collect(Collectors.toList()));
    }
}
