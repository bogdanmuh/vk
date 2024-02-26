package vk.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.domain.ERole;
import vk.domain.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(ERole name);

    Boolean existsByName(ERole name);

}
