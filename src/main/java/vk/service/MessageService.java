package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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

    public void saveMessage(ChatRequest chatRequest) {
        Optional<User> sender = userService.getUser(chatRequest.getSender());
        Optional<User> recipient = userService.getUser(chatRequest.getRecipient());
        if (sender.isEmpty() || recipient.isEmpty()) {
            System.out.println("Невозможно сохранить сообщенение отправитель или получатель не найдены ");
            return;
        }
        Optional<Chats> chat = chatsService.findById(chatRequest.getChatId());
        if (chat.isPresent()){
            Message message = new Message(null,
                    chat.get(),
                    sender.get(),
                    new Date(),
                    chatRequest.getMessage());
            messageRepository.save(message);
        } else {
            System.out.println("Невозможно сохранить сообщенение неверно указан chat_id");
        }
    }

    public ChatMessageResponse getMessage(Long chat_id) {
        return new ChatMessageResponse(
                messageRepository.getMessage(chat_id),
                true,
                chat_id);
    }

    public ChatMessageResponse getMessage(String [] usernames) {
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

    public List<AllChatResponse> getLastMesasgeFromChats(Long user_id) {
        List<AllChatResponse> list = messageRepository.getLastMesasgeFromChats(user_id);
        for (AllChatResponse response : list) {
            User compains = chatsService.getCompains(response.getChat_id(), user_id);// написать один запрос
            response.setUsernameCompains(compains.getUsername());
            response.setFirstNameCompains(compains.getFirstName());
            response.setLastNameCompains(compains.getLastName());
        }
        return list;
    }

}
