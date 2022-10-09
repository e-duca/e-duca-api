package educa.api.controller;

import educa.api.domain.Estudante;
import educa.api.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios/estudantes")
public class EstudanteController {

    @Autowired
    private EstudanteRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Estudante> create(@RequestBody @Valid Estudante estudante) {
        estudante.setSenha(encoder.encode(estudante.getSenha()));
        return ResponseEntity.status(201).body(repository.save(estudante));
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> read() {
        List<Estudante> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Estudante>> read(@PathVariable int id) {
        Optional<Estudante> estudante = repository.findById(id);
        return estudante.isPresent()
                ? ResponseEntity.status(200).body(estudante)
                : ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> update(
            @PathVariable int id,
            @RequestBody @Valid Estudante estudante) {
        if (repository.existsById(id)) {
            estudante.setId(id);
            repository.save(estudante);
            return ResponseEntity.status(200).body(estudante);
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
    public ResponseEntity<Estudante> loginEstudante(@RequestBody Estudante validaEstudante) {

        Optional<Estudante> optionalEstudante = repository.findByEmail(validaEstudante.getEmail());
        Estudante estudante  = optionalEstudante.get();

        if (!(optionalEstudante.isEmpty()) && encoder.matches(validaEstudante.getSenha(), estudante.getSenha())) {
            estudante.setAutenticado(true);
            repository.save(estudante);
            return ResponseEntity.status(200).body(estudante);
        } else {
            return ResponseEntity.status(401).build();
        }
    }

    @DeleteMapping("/logoff/{id}")
    public ResponseEntity logoffEstudante(@PathVariable int id) {
        List<Estudante> list = repository.findAll();

        for (Estudante estudanteAtual : list) {
            if (estudanteAtual.getId() == id) {
                if (estudanteAtual.isAutenticado()) {
                    estudanteAtual.setAutenticado(false);
                    repository.save(estudanteAtual);
                    return ResponseEntity.status(200).body(String.format("Logoff do usuário %s concluído", estudanteAtual.getNome()));
                } else {
                    return ResponseEntity.status(401).body(String.format("Usuário %s NÃO está autenticado", estudanteAtual.getNome()));
                }
            }
        }
        return ResponseEntity.status(403).body(String.format("Usuário do Id %d não encontrado", id));
    }
}
