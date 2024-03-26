package vk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import vk.domain.dto.ChatDto;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chats")
@NamedNativeQuery(name = "Chats.findChats",
        query = "select uc.chat_id as id , count(uc.chat_id) as count" +
                "                    from user_chat as uc" +
                "                                 where uc.user_id = 1 or uc.user_id = 2" +
                "                                 group by uc.chat_id" +
                "                                 having count(uc.chat_id) = 2" +
                "                                 LIMIT 1",
        resultSetMapping = "Mapping.ChatDto")
@SqlResultSetMapping(name = "Mapping.ChatDto",
        classes = @ConstructorResult(targetClass = ChatDto.class,
                columns = {
                @ColumnResult(name = "id", type = Long.class),
                @ColumnResult(name = "count", type = Long.class)}
        ))

public class Chats {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="user_chat",
            joinColumns =  @JoinColumn(name="chat_id"),
            inverseJoinColumns= @JoinColumn(name="user_id"))
    private Set<User> users = new HashSet<>();

}
