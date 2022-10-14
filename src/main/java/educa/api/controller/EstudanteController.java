package educa.api.controller;

import educa.api.domain.Usuario;
import educa.api.repository.UsuarioRepository;
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
    private UsuarioRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody @Valid Usuario estudante) {
        estudante.setSenha(encoder.encode(estudante.getSenha()));
        return ResponseEntity.status(201).body(repository.save(estudante));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> read() {
        List<Usuario> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> read(@PathVariable int id) {
        Optional<Usuario> estudante = repository.findById(id);
        return estudante.isPresent()
                ? ResponseEntity.status(200).body(estudante)
                : ResponseEntity.status(204).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(
            @PathVariable int id,
            @RequestBody @Valid Usuario estudante) {
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

}
