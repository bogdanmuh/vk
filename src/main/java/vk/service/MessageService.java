package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.controller.pojo.AllChatResponse;
import vk.controller.pojo.ChatMessageResponse;
import vk.controller.pojo.ChatRequest;
import vk.controller.pojo.ChatResponse;
import vk.domain.Message;
import vk.domain.User;
import vk.repos.MessageRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;

    public void saveMessage(ChatRequest chatRequest) {
        Optional<User> from = userService.getUser(chatRequest.getFrom());
        Optional<User> to = userService.getUser(chatRequest.getTo());
        if (from.isPresent() && to.isPresent()) {
            Message message = new Message();
            message.setSender(from.get());
            message.setRecipient(to.get());
            message.setMessage(chatRequest.getMessage());
            message.setDate(new Date());
            messageRepository.save(message);
        } else {
            System.out.println("Невозможно сохранить сообщенение отправитель или получатель  не найдены ");
        }
    }
    public ChatMessageResponse getMessage(String from, String to) {
        List<ChatResponse> message = messageRepository.getMessage(from, to).stream()
                .map(x -> new ChatResponse(
                        x.getSender().getUsername(),
                        x.getRecipient().getUsername(),
                        x.getDate(),
                        x.getMessage(),
                        ""))
                .collect(Collectors.toList());

        return new ChatMessageResponse(message, userService.isOnline(to));
    }

    public List<Message> getMessage(ChatRequest chatRequest) {
        return messageRepository.getMessage(
                chatRequest.getFrom(),
                chatRequest.getTo(),
                chatRequest.getDate());
    }

    public List <AllChatResponse> getLastMesasgeForEachRecipient(String username){
        return messageRepository.findDistinctTop20ByRecipient(username).stream()
                .map(x -> new AllChatResponse(
                        x.getSender().getUsername(),
                        x.getSender().getFirstName(),
                        x.getSender().getLastName(),
                        x.getDate(),
                        x.getMessage()))
                .collect(Collectors.toList());
    }


}
