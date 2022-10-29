package educa.api.repository;

import educa.api.domain.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {

    Perfil findByNome(String nome);

}
