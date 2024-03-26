package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.controller.exception.ChatNotFoundException;
import vk.controller.exception.UserNofFoundException;
import vk.controller.pojo.NewChatRequest;
import vk.domain.Chats;
import vk.domain.User;
import vk.repos.ChatsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatsService {

    private final ChatsRepository chatsRepository;
    private final UserService userService;

    public Chats save(NewChatRequest newChatRequest) throws UserNofFoundException {
        Set<User> users = userService.getUsers(newChatRequest.getParticipantsUsername());
        Optional<Chats> chatsData = getChats(users);
        if (chatsData.isEmpty())
            return chatsRepository.save(new Chats(null, newChatRequest.getName(), users));
        return chatsData.get();
    }
    public Optional<Chats> getChats(Set<User> users) {
        if (users.size() == 2) {
            List<User> list = new ArrayList<>(users);
            List<Chats> chats = chatsRepository.findChats(list.get(0).getId(),list.get(1).getId());
            if (chats.size() == 0) {
                return Optional.empty();
            }
            return Optional.of(chats.get(0));
        }
        return Optional.empty();
    }

    public Chats findById(Long id) throws ChatNotFoundException {
        return chatsRepository.findById(id)
                .orElseThrow(() -> new ChatNotFoundException(String.format(ChatNotFoundException.NotFound,id)));
    }

    public User getCompanions(Long chat_id, Long user_id) throws ChatNotFoundException, UserNofFoundException {
        Chats chat = findById(chat_id);
        Long idCompanion = null;
        Set<User> users = chat.getUsers();
        if (users.size() == 2) {
            for (User user : users) {
                if (!user_id.equals(user.getId())){
                    idCompanion = user.getId();
                }
            }
        }
        return userService.getUser(idCompanion);
    }

    public List<Chats> findCompanions(Long user_id){
        return null;// chatsRepository.findByUsers_User_id(user_id);
    }

}
