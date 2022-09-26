package educa.api.controller;

import educa.api.model.Estudante;
import educa.api.model.Professor;
import educa.api.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios/professores")
public class ProfessorController {

    @Autowired
    private ProfessorRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping
    public ResponseEntity<List<Professor>> mostrarProfessores() {
        List<Professor> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @PostMapping
    public ResponseEntity<Professor> cadastrarProfessor(@RequestBody Professor professor) {
        professor.setSenha(encoder.encode(professor.getSenha()));
        return ResponseEntity.status(201).body(repository.save(professor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Professor> updateProfessor(@PathVariable int id, @RequestBody Professor newProfessor) {
        return repository.findById(id)
                .map(professor -> {
                    professor.setNome(newProfessor.getNome());
                    professor.setDataNasc(newProfessor.getDataNasc());
                    professor.setEmail(newProfessor.getEmail());
                    professor.setSenha(encoder.encode(newProfessor.getSenha()));
                    professor.setAreaAtuacao(newProfessor.getAreaAtuacao());
                    professor.setTempoCarreira(newProfessor.getTempoCarreira());
                    return ResponseEntity.status(201).body(repository.save(professor));
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(400).build();
                });
    }

    @PostMapping("/login")
    public ResponseEntity<Professor> loginProfessor(@RequestBody Professor validaProfessor) {

        Optional<Professor> optionalProfessor = repository.findByEmail(validaProfessor.getEmail());
        Professor professor  = optionalProfessor.get();

        if (!(optionalProfessor.isEmpty()) && encoder.matches(validaProfessor.getSenha(), professor.getSenha())) {
            repository.updateAuthenticated(true, professor.getId());
            return ResponseEntity.status(200).body(professor);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/logoff/{id}")
    public ResponseEntity logoffProfessor(@PathVariable int id) {
        List<Professor> list = repository.findAll();

        for (Professor professorAtual : list) {
            if (professorAtual.getId().equals(id)) {
                if (professorAtual.isAutenticado()) {
                    repository.updateAuthenticated(false, id);
                    return ResponseEntity.status(200).body(String.format("Logoff do usuário %s concluído", professorAtual.getNome()));
                } else {
                    return ResponseEntity.status(401).body(String.format("Usuário %s NÃO está autenticado", professorAtual.getNome()));
                }
            }
        }
        return ResponseEntity.status(403).body(String.format("Usuário do Id %d não encontrado", id));
    }
}
