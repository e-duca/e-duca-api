package educa.api.repository;

import educa.api.domain.Conteudo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConteudoRepository extends JpaRepository<Conteudo, Integer> {

    Page<Conteudo> findByTitulo(String titulo, Pageable paginacao);

    Page<Conteudo> findByAutorId(int id, Pageable paginacao);

    Page<Conteudo> findByTituloAndAutorId(String titulo, int id, Pageable paginacao);

}
