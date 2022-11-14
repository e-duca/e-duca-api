package educa.api.repository;

import educa.api.request.domain.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}
