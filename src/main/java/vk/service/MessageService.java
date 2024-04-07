package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.controller.pojo.AllChatResponse;
import vk.controller.pojo.ChatMessageResponse;
import vk.domain.Chats;
import vk.domain.Message;
import vk.domain.User;
import vk.repos.MessageRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public void saveMessage(User sender,Chats chat,String text) {
        messageRepository.save(new Message(null,
                chat,
                sender,
                new Date(),
                text));
    }

    public ChatMessageResponse getMessage(Long chat_id, boolean isOnline) {
        return new ChatMessageResponse(
                messageRepository.getMessage(chat_id),
                isOnline,
                chat_id);
    }

    public List<Message> getMessage(Long chat_id, Date date) {
        return messageRepository.getMessage(chat_id, date);
    }

    public List<AllChatResponse> getLastMessageFromChats(Long user_id) {
        return messageRepository.getLastMessageFromChats(user_id);
    }

}
