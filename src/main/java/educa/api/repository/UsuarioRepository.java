package educa.api.repository;

import educa.api.request.domain.Usuario;
import educa.api.response.UsuarioEstudanteResponse;
import educa.api.response.UsuarioProfessorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    @Query("select new " +
            "educa.api.response.UsuarioProfessorResponse(u.idUsuario, u.nome, u.sobrenome, u.email, u.areaAtuacao, u.inicioAtuacao) " +
            "from Usuario u where u.idUsuario = ?1 and u.areaAtuacao != null")
    Optional<UsuarioProfessorResponse> getProfessorById(int idUsuario);

    @Query("select new " +
            "educa.api.response.UsuarioProfessorResponse(u.idUsuario, u.nome, u.sobrenome, u.email, u.areaAtuacao, u.inicioAtuacao) " +
            "from Usuario u where u.areaAtuacao != null")
    List<UsuarioProfessorResponse> getProfessores();

    @Query("select new " +
            "educa.api.response.UsuarioEstudanteResponse(u.idUsuario, u.nome, u.sobrenome, u.email) " +
            "from Usuario u where u.idUsuario = ?1 and u.areaAtuacao = null")
    Optional<UsuarioEstudanteResponse> getEstudanteById(int idUsuario);

    @Query("select new " +
            "educa.api.response.UsuarioEstudanteResponse(u.idUsuario, u.nome, u.sobrenome, u.email) " +
            "from Usuario u where u.areaAtuacao = null")
    List<UsuarioEstudanteResponse> getEstudantes();

}
