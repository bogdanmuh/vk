package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.domain.User;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(Long id);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username); // union
    Boolean existsByEmail(String username);
    List<User> findAll();// think!!!!!!!!!
    Optional<User> findByActivateCode(String code);

}
