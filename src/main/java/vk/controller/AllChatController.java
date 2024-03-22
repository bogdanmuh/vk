package vk.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.service.MessageService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@AllArgsConstructor
public class AllChatController {

    private final MessageService messageService;

    @GetMapping("/allChat")
    public ResponseEntity<?> getCompanions(@RequestParam Long user_id) {
        return ResponseEntity.ok(messageService.getLastMesasgeFromChats(user_id));
    }

}
