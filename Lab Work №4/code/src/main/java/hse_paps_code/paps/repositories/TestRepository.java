package hse_paps_code.paps.repositories;

import hse_paps_code.paps.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
