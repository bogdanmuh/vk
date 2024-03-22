package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vk.domain.Chats;
import vk.domain.dto.ChatDto;

import java.util.List;
import java.util.Optional;

public interface ChatsRepository extends JpaRepository<Chats, Long> {

    List<Chats> findByUsers(String users);

    Optional<Chats> findById(Long users);

/*select new vk.domain.dto.ChatDto(" +
            "   uc.id, " +
            "   count(uc.id) " +
            ")" +*/
    @Query( "Select C " +
            " from Chats C " +
            " inner join C.users uc" +
            " where uc.id = :user_id1 or uc.id = :user_id2 " +
            " group by C ")
            //" having count(C) = 2")
    List <Chats> findChats(Long user_id1, Long user_id2);
}