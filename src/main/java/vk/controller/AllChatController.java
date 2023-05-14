package vk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.service.MessageService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class AllChatController {
    @Autowired
    private MessageService messageService;
    @GetMapping("/allChat")
    public ResponseEntity<?> getCompanions(@RequestParam String to) {
        return ResponseEntity.ok(messageService.getLastMesasgeForEachRecipient(to));
    }
}
