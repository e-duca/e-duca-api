package educa.api.repository;

import educa.api.domain.Estudante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EstudanteRepository extends JpaRepository<Estudante, Integer> {

    public Optional<Estudante> findByEmail(String email);

}
