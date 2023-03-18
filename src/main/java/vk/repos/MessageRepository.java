package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import vk.domain.Message;

import java.util.Date;
import java.util.List;


public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("From Message as M Where M.sender.username = :username AND  M.recipient.username = :username1")
    List<Message> getMessage(@Param("username") String username,
                             @Param("username1") String username1);
    @Query("From Message as M Where M.sender.username = :username AND  M.recipient.username = :username1 AND M.date < :date")
    List<Message> getMessage(@Param("username") String username,
                      @Param("username1") String username1,
                      @Param("date") Date date);
}

