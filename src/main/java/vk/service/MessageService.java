package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.controller.exception.ChatNotFoundException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.pojo.AllChatResponse;
import vk.controller.pojo.ChatMessageResponse;
import vk.controller.pojo.ChatRequest;
import vk.domain.Chats;
import vk.domain.Message;
import vk.domain.User;
import vk.repos.MessageRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;
    private final ChatsService chatsService;

    public void saveMessage(ChatRequest chatRequest) throws UserNofFoundException, ChatNotFoundException {
        User sender = userService.getUser(chatRequest.getSender());
        Chats chat = chatsService.findById(chatRequest.getChatId());
        Message message = new Message(null,
                chat,
                sender,
                new Date(),
                chatRequest.getMessage());
        messageRepository.save(message);
    }

    public ChatMessageResponse getMessage(Long chat_id) {
        return new ChatMessageResponse(
                messageRepository.getMessage(chat_id),
                true,
                chat_id);
    }

    public ChatMessageResponse getMessage(String [] usernames) throws UserNofFoundException {
        Optional<Chats> chats = chatsService.getChats(userService.getUsers(usernames));
        if (chats.isPresent()) {
            return new ChatMessageResponse(
                    messageRepository.getMessage(chats.get().getId()),
                    true,
                    chats.get().getId());
        }
        return new ChatMessageResponse(
                 Collections.emptyList(), true, -1L);
    }

    public List<Message> getMessage(Long chat_id, Date date) {
        return messageRepository.getMessage(chat_id, date);
    }

    public List<AllChatResponse> getLastMessageFromChats(Long user_id) throws ChatNotFoundException, UserNofFoundException {
        List<AllChatResponse> list = messageRepository.getLastMessageFromChats(user_id);
        for (AllChatResponse response : list) {
            User companion = chatsService.getCompanions(response.getChat_id(), user_id);// написать один запрос
            response.setUsernameCompanion(companion.getUsername());
            response.setFirstNameCompanion(companion.getFirstName());
            response.setLastNameCompanion(companion.getLastName());
        }
        return list;
    }

}
