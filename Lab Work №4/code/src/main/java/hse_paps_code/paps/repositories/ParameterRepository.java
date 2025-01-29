package hse_paps_code.paps.repositories;

import hse_paps_code.paps.entities.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParameterRepository extends JpaRepository<Parameter, Long> {
}
