package vk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vk.controller.pojo.AllChatResponse;

import javax.persistence.*;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
@NamedNativeQuery(name = "Message.getLastMessageFromChats",
       query = "select chats.name as name, usC, fnC, lnC, us, fn, ln, maxdate, mes, cid from chats inner join " +
               "    (select users.username as usC, users.first_name as fnC, users.last_name as lnC, us, fn, ln, maxdate, mes, cid from users inner join " +
               "        (select user_chat.user_id, us, fn, ln, maxdate, mes, cid from user_chat inner join " +
               "            (select a.user_id, u.username as us, u.first_name as fn, u.last_name as ln, maxdate , a.message as mes, a.chat_id as cid " +
               "                from users u " +
               "                inner join " +
               "                    (select c.chat_id, c.maxdate, m.message, m.user_id " +
               "                    from messages m " +
               "                    inner join " +
               "                        (select uc.chat_id, max(m.date) as maxdate " +
               "                            from user_chat uc " +
               "                            inner join messages m on m.chat_id = uc.chat_id " +
               "                                where uc.user_id = ?1 " +
               "                            group by uc.chat_id " +
               "                            order by maxdate) as c " +
               "                on c.chat_id = m.chat_id and c.maxdate = m.date) as a " +
               "                on a.user_id = u.id) as n " +
               "        on user_chat.chat_id = n.cid " +
               "        where user_chat.user_id != ?1 ) as n " +
               "    on n.user_id =  users.id) as k " +
               "on k.cid = chats.id ",
        resultSetMapping = "Mapping.MessageDto")
@SqlResultSetMapping(name = "Mapping.MessageDto",
        classes = @ConstructorResult(targetClass = AllChatResponse.class,
                columns = {@ColumnResult(name = "us"),
                        @ColumnResult(name = "fn"),
                        @ColumnResult(name = "ln"),
                        @ColumnResult(name = "maxdate"),
                        @ColumnResult(name = "mes"),
                        @ColumnResult(name = "cid" , type = Long.class),
                        @ColumnResult(name = "usC"),
                        @ColumnResult(name = "fnC"),
                        @ColumnResult(name = "lnC"),
                        @ColumnResult(name = "name"),
                        }))


public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chats chat_id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    private Date date;
    private String message;

}
