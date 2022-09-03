package educa.api.repository;

import educa.api.model.UsuarioModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, Integer> {

    public Optional<UsuarioModel> findByUsername(String username);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Usuario set authenticated=:status WHERE ID = :idUser")
    public void updateAuthenticated(@Param("status") boolean status, @Param("idUser") int idUser);
}
