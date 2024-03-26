package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vk.controller.exception.ChatNotFoundException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.pojo.AllChatResponse;
import vk.service.MessageService;

import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
@AllArgsConstructor
public class AllChatController {

    private final MessageService messageService;

    @GetMapping("/allChat")
    public List<AllChatResponse> getCompanions(@RequestParam Long user_id) throws ChatNotFoundException, UserNofFoundException {
        return messageService.getLastMessageFromChats(user_id);
    }

}
