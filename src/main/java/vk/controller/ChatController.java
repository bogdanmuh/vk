package vk.controller;

import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import vk.controller.exception.ChatNotFoundException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.pojo.*;
import vk.domain.Chats;
import vk.domain.Message;
import vk.domain.User;
import vk.service.ChatsService;
import vk.service.MessageService;
import vk.service.UserService;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200",maxAge = 3600)
@AllArgsConstructor
//@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatsService chatsService;
    private final UserService userService;

    @MessageMapping("/topic")
    public void processMessage(@Payload ChatRequest chatMessage)
            throws UserNofFoundException, ChatNotFoundException {
        User sender = userService.getUser(chatMessage.getSender());
        Chats chat = chatsService.findById(chatMessage.getChatId());
        messageService.saveMessage(sender,chat,chatMessage.getMessage());
        messagingTemplate.convertAndSendToUser(
                chatMessage.getRecipient(),
                "/queue/messages",
                    new ChatResponse(
                            chatMessage.getSender(),
                            chatMessage.getDate(),
                            chatMessage.getMessage(),
                            "message"));

    }

    @GetMapping(value = "/chat", params = { "id", "sender" })
    public ChatMessageResponse getMessage(@RequestParam Long id, @RequestParam String sender)
            throws ChatNotFoundException, UserNofFoundException {
        User companion = userService.getUser(chatsService.getCompanionsId(id, sender));
        boolean isCompanionOnline = userService.isOnline(companion.getUsername());
        return messageService.getMessage(id,isCompanionOnline);
    }

    @GetMapping(value = "/chat", params = { "companion", "sender" })
    public ChatMessageResponse getMessage(@RequestParam String  companion, @RequestParam String sender) throws UserNofFoundException {
        User senderData = userService.getUser(sender);
        User companionData = userService.getUser(companion);
        boolean isCompanionOnline = userService.isOnline(companion);
        Optional<Chats> chats = chatsService.getChatsForTwo(senderData, companionData);
        if (chats.isPresent()) {
            return messageService.getMessage(chats.get().getId(), isCompanionOnline);

        }
        return messageService.getMessage(-1L, isCompanionOnline);
    }

    @PostMapping("/chat/new")
    public ChatData newChat(@RequestBody NewChatRequest newChatRequest) throws UserNofFoundException {
        HashSet<User> usersSet = userService.getUsers(newChatRequest.getParticipantsUsername());
        Chats chats = chatsService.save(usersSet, newChatRequest.getName());
        return new ChatData(chats.getId(),chats.getName());
    }

    @PostMapping("/chat/update")
    public List<Message> getMessage(@RequestParam Long id, @RequestParam Date date) {
        return messageService.getMessage(id, date);
    }

}
