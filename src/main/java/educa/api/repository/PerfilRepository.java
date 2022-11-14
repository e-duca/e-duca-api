package educa.api.repository;

import educa.api.request.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    Perfil findByNome(String nome);

}
