package vk.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import vk.controller.exception.ChatNotFoundException;
import vk.domain.Chats;
import vk.domain.User;
import vk.repos.ChatsRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ChatsService {

    private final ChatsRepository chatsRepository;

    public Chats save(Set<User> usersSet,String name) {
        if (usersSet.size() == 2) {
            User[] users = usersSet.toArray(new User[0]);
            Optional<Chats> chatsData = getChatsForTwo(users[0], users[1]);
            if (chatsData.isPresent()) {
                return chatsData.get();
            }
            return chatsRepository.save(new Chats(null, name, usersSet));

        }
        return null;
    }
    public Optional<Chats> getChatsForTwo(User user1, User user2) {
        List<Chats> chats = chatsRepository.findChats(user1.getId(), user2.getId());
        if (chats.size() == 0) {
            return Optional.empty();
        }
        return Optional.of(chats.get(0));
    }

    public Chats findById(Long id) throws ChatNotFoundException {
        return chatsRepository.findById(id)
                .orElseThrow(() -> new ChatNotFoundException(String.format(ChatNotFoundException.NotFound,id)));
    }

    public Long getCompanionsId(Long chat_id, String username) throws ChatNotFoundException {
        Chats chat = findById(chat_id);
        Long idCompanion = null;
        Set<User> users = chat.getUsers();
        if (users.size() == 2) {
            for (User user : users) {
                if (!username.equals(user.getUsername())){
                    idCompanion = user.getId();
                }
            }
        }
        return idCompanion;
    }

}
