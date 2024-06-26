package vk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Getter
@Entity
@NoArgsConstructor
@Table(name = "friends")
public class Friends {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "friend_one")
    private User friend_one;

    @ManyToOne
    @JoinColumn(name = "friend_two")
    private User friend_two;

    public Friends(User friend_one, User friend_two) {
        this.friend_one = friend_one;
        this.friend_two = friend_two;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj instanceof Friends) {
            return ((Friends) obj).getFriend_one().equals(getFriend_two()) &&
                    ((Friends) obj).getFriend_two().equals(getFriend_one());
        }
        return false;
    }

}
