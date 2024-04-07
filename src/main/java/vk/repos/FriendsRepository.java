package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vk.domain.Friends;

import java.util.List;

public interface FriendsRepository extends JpaRepository<Friends, Long> {

    @Query("From Friends as F Where F.friend_one.username = :username or F.friend_two.username = :username ")
    List<Friends> getFriends(@Param("username") String user);

}
