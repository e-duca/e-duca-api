package educa.api.repository;

import educa.api.request.domain.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    Page<Topico> findByTitulo(String titulo, Pageable paginacao);

}
