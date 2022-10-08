package educa.api.repository;

import educa.api.domain.Conteudo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<Conteudo, Integer> {
}
