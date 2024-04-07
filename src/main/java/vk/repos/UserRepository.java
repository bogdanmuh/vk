package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vk.domain.User;
import vk.domain.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select new vk.domain.dto.UserDto(" +
            " U.id, " +
            " U.username, " +
            " U.firstName, " +
            " U.lastName " +
            " ) " +
            "from User U where U.username like '%1%' or U.firstName like '%1%' or U.lastName like '%1%'")
    List<UserDto> findByText(String str);
    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username); // union
    Boolean existsByEmail(String username);
    List<User> findAll();// think!!!!!!!!!
    Optional<User> findByActivateCode(String code);

}
