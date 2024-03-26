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
    List<Message> getMessage(@Param("chat_id") Long chat_id,
                      @Param("date") Date date);


    // @Query("select distinct M.chat_id, M.message, max(M.date) From Message as M Where M.chat_id = ?1 group by M.chat_id")
   // @Query("select distinct M.chat_id, M.message, max(M.date) From user_chat as M Where M.chat_id = ?1 group by M.chat_id")
    //@Query(value = "From messages inner join" +
    //        "    (Select Distinct  M.chat_id, max(M.date)  From Message as M Where M.chat_id = ?1 group by M.chat_id) AS a" +
    //        "        on a.sender = messages.sender AND a.max = messages.date")// limit 20


    /*@Query(value =
    "select * " +
    "from users u " +
    "    INNER join " +
    "     (select m.chat_id, m.user_id, max(m.date) as maxdate, m.message " +
    "     from user_chat uc" +
    "     INNER join messages m on m.user_id = uc.user_id " +
    "         where m.user_id = ?1 " +
    "     group by m.chat_id, m.user_id, m.message, m.date " +
    "     order by m.date) as a " +
    "     on " +
    "     a.user_id = u.id ", nativeQuery = true)// limit 20
    @Query(value =
    " select new vk.controller.pojo.AllChatResponse(" +
        " u.username, " +
        " u.first_name, " +
        " u.last_name, " +
        " maxdate, " +
        " m.message, " +
        " m.chat_id.id " +
        " ) " +
    " from users u " +
    " where u.id in  " +
    "     (select m.chat_id, m.user_id, max(m.date) as maxdate, m.message " +
    "     from user_chat uc" +
    "     INNER join messages m on m.user_id = uc.user_id " +
    "         where m.user_id = :user_id" +
    "     group by m.chat_id, m.user_id, m.message, m.date " +
    "     order by m.date)  " )  // limit 20*/
    @Query(nativeQuery = true)
    List <AllChatResponse> getLastMessageFromChats(Long user_id);




/*select messages.chat_id, max(messages.date), messages.message from user_chat
INNER join messages on messages.user_id = user_chat.user_id
group by messages.chat_id,messages.message

select messages.chat_id, messages.user_id, max(messages.date), messages.message from user_chat
INNER join messages on messages.user_id = user_chat.user_id
group by messages.chat_id,messages.message, messages.user_id


*/
}

