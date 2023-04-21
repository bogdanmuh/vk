package vk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vk.controller.pojo.ChatRequest;
import vk.controller.pojo.ChatResponse;
import vk.domain.Message;
import vk.domain.User;
import vk.repos.MessageRepository;
import vk.repos.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;


    public  void saveMessage(ChatRequest chatRequest) {
        User from = userRepository.findByUsername(chatRequest.getFrom()).get();
        User to = userRepository.findByUsername(chatRequest.getTo()).get();

        Message message = new Message();
        message.setSender(from);
        message.setRecipient(to);
        message.setMessage(chatRequest.getMessage());
        message.setDate(new Date());

        messageRepository.save(message);
    }

    public List<ChatResponse> getMessage(String from, String to) {
        return messageRepository.getMessage(from, to).stream()
                .map(x -> new ChatResponse(
                        x.getSender().getUsername(),
                        x.getRecipient().getUsername(),
                        x.getDate(),
                        x.getMessage()))
                .collect(Collectors.toList());
    }

    public List<Message> getMessage(ChatRequest chatRequest) {
        return messageRepository.getMessage(
                chatRequest.getFrom(),
                chatRequest.getTo(),
                chatRequest.getDate());
    }


}
