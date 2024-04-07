package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.controller.pojo.AllChatResponse;
import vk.controller.pojo.ChatResponse;
import vk.domain.Message;

import java.util.Date;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("select new vk.controller.pojo.ChatResponse(" +
            " M.user_id.username, " +
            " M.date, " +
            " M.message " +
            " ) " +
            "From Message as M Where M.chat_id.id = :chat_id")
    List<ChatResponse> getMessage(@Param("chat_id") Long chat_id);

    @Query("From Message as M Where M.chat_id.id = :chat_id AND M.date < :date")
    List<Message> getMessage(@Param("chat_id") Long chat_id, @Param("date") Date date);

    @Query(nativeQuery = true)
    List <AllChatResponse> getLastMessageFromChats(Long user_id);

}

