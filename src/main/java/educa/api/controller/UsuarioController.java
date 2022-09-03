package educa.api.controller;

import educa.api.model.UsuarioModel;
import educa.api.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

    private final UsuarioRepository repository;
    private final PasswordEncoder encoder;

    public UsuarioController(UsuarioRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @GetMapping
    public ResponseEntity<List<UsuarioModel>> mostrarTodosUsers() {
        return ResponseEntity.ok(repository.findAll());
    }

    @PostMapping
    public ResponseEntity<UsuarioModel> cadastrarUser(@RequestBody UsuarioModel usuarioModel) {
        usuarioModel.setPassword(encoder.encode(usuarioModel.getPassword()));
        return ResponseEntity.ok(repository.save(usuarioModel));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UsuarioModel usuario) {

        Optional<UsuarioModel> optionalUsuarioModel = repository.findByUsername(usuario.getUsername());

        UsuarioModel usuarioModel  = optionalUsuarioModel.get();

        if (!(optionalUsuarioModel.isEmpty()) && encoder.matches(usuario.getPassword(), usuarioModel.getPassword())) {
            repository.updateAuthenticated(true, usuarioModel.getId());
            return ResponseEntity.status(HttpStatus.OK).body("Usuário " + usuarioModel.getNome() + " está autenticado!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário e/ou senha inválidos!!");
        }
    }

    @DeleteMapping("/logoff/{id}")
    public String logoffUser(@PathVariable int id) {
        repository.updateAuthenticated(false, id);
        return "Usuário deslogado com sucesso!";
    }
}
