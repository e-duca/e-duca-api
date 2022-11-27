package educa.api.repository;

import educa.api.request.domain.Topico;
import educa.api.request.domain.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Integer> {
    Page<Topico> findByTitulo(String titulo, Pageable paginacao);

    Page<Topico> findByTituloAndUsuarioIdUsuario(String titulo, int idUsuario, Pageable paginacao);

    Page<Topico> findByUsuarioIdUsuario(int idUsuario, Pageable paginacao);

}
