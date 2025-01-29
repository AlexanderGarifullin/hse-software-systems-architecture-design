package hse_paps_code.paps.repositories;

import hse_paps_code.paps.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
