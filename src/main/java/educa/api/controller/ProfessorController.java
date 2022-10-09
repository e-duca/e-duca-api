package educa.api.controller;

import educa.api.domain.Professor;
import educa.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Professor> create(@RequestBody @Valid Professor professor) {
        professor.setSenha(encoder.encode(professor.getSenha()));
        return ResponseEntity.status(201).body(repository.save(professor));
    }

    @GetMapping
    public ResponseEntity<List<Professor>> read() {
        List<Professor> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Professor>> readById(@PathVariable int id) {
        Optional<Professor> professor = repository.findById(id);
        return professor.isPresent()
                ? ResponseEntity.status(200).body(professor)
                : ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(
            @PathVariable int id,
            @RequestBody @Valid Professor professor) {
        if (repository.existsById(id)) {
            professor.setId(id);
            repository.save(professor);
            return ResponseEntity.status(200).body(professor);
        }
        return ResponseEntity.status(404).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.status(404).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Professor> loginProfessor(@RequestBody @Valid Professor validaProfessor) {

        Optional<Professor> optionalProfessor = repository.findByEmail(validaProfessor.getEmail());
        Professor professor  = optionalProfessor.get();

        if (!(optionalProfessor.isEmpty()) && encoder.matches(validaProfessor.getSenha(), professor.getSenha())) {
            professor.setAutenticado(true);
            repository.save(professor);
            return ResponseEntity.status(200).body(professor);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/logoff/{id}")
    public ResponseEntity logoffProfessor(@PathVariable int id) {
        List<Professor> list = repository.findAll();

        for (Professor professorAtual : list) {
            if (professorAtual.getId() == id) {
                if (professorAtual.isAutenticado()) {
                    professorAtual.setAutenticado(false);
                    repository.save(professorAtual);
                    return ResponseEntity.status(200).body(String.format("Logoff do usuário %s concluído", professorAtual.getNome()));
                } else {
                    return ResponseEntity.status(401).body(String.format("Usuário %s NÃO está autenticado", professorAtual.getNome()));
                }
            }
        }
        return ResponseEntity.status(403).body(String.format("Usuário do Id %d não encontrado", id));
    }
}
