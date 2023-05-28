package educa.api.repository;

import educa.api.request.domain.Avaliacao;
import educa.api.response.AvaliacaoResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    @Query("SELECT NEW educa.api.response.AvaliacaoResponse(COUNT(av.avaliacao)) " +
            "FROM Avaliacao av " +
            "JOIN av.conteudo co " +
            "JOIN co.usuario us " +
            "WHERE us.idUsuario = ?1")
    Optional<AvaliacaoResponse> getAvaliacaoCountByIdProfessor(int idUsuario);
}
