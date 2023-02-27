package educa.api.repository;

import educa.api.request.domain.Habilidade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HabilidadeRepository extends JpaRepository<Habilidade, Integer> {

    Optional<Habilidade> findByCodigo(String codigo);

}
