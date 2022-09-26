package educa.api.controller;

import educa.api.model.Estudante;
import educa.api.repository.EstudanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Estudante> cadastrarEstudante(@RequestBody Estudante estudante) {
        estudante.setSenha(encoder.encode(estudante.getSenha()));
        return ResponseEntity.status(201).body(repository.save(estudante));
    }

    @GetMapping
    public ResponseEntity<List<Estudante>> mostrarEstudantes() {
        List<Estudante> list = repository.findAll();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudante> updateEstudante(@PathVariable int id, @RequestBody Estudante newEstudante) {
        return repository.findById(id)
                .map(estudante -> {
                    estudante.setNome(newEstudante.getNome());
                    estudante.setDataNasc(newEstudante.getDataNasc());
                    estudante.setEmail(newEstudante.getEmail());
                    estudante.setSenha(encoder.encode(newEstudante.getSenha()));
                    return ResponseEntity.status(201).body(repository.save(estudante));
                })
                .orElseGet(() -> {
                    return ResponseEntity.status(400).build();
                });
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
            if (estudanteAtual.getId().equals(id)) {
                if (estudanteAtual.isAutenticado()) {
                    repository.updateAuthenticated(false, id);
                    return ResponseEntity.status(200).body(String.format("Logoff do usuário %s concluído", estudanteAtual.getNome()));
                } else {
                    return ResponseEntity.status(401).body(String.format("Usuário %s NÃO está autenticado", estudanteAtual.getNome()));
                }
            }
        }
        return ResponseEntity.status(403).body(String.format("Usuário do Id %d não encontrado", id));
    }
}
