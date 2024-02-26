package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.domain.Message;

import java.util.Date;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("From Message as M Where M.sender.username = :username AND  M.recipient.username = :username1 OR M.sender.username = :username1 AND  M.recipient.username = :username")
    List<Message> getMessage(@Param("username") String username,
                             @Param("username1") String username1);

    @Query("From Message as M Where M.sender.username = :username AND  M.recipient.username = :username1 AND M.date < :date")
    List<Message> getMessage(@Param("username") String username,
                      @Param("username1") String username1,
                      @Param("date") Date date);

    @Query(value = "select * From messages inner join" +
            "    (select distinct b.sender, max(b.date) from messages as b  where b.recipient = ?1 group by b.sender) AS a" +
            "        on a.sender = messages.sender AND a.max = messages.date", nativeQuery = true)// limit 20
    List <Message> findDistinctTop20ByRecipient( String username);

}

