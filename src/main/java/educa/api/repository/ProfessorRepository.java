package educa.api.repository;

import educa.api.domain.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfessorRepository extends JpaRepository<Professor, Integer> {

    public Optional<Professor> findByEmail(String email);

}
