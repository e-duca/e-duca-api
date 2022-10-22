package educa.api.controller;

import educa.api.domain.Usuario;
import educa.api.controller.dto.UsuarioEstudanteDto;
import educa.api.controller.form.EstudanteForm;
import educa.api.repository.UsuarioRepository;
import educa.api.utils.ListObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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
    public ResponseEntity<UsuarioEstudanteDto> create(@RequestBody @Valid EstudanteForm form, UriComponentsBuilder uriBuilder) {
        form.setSenha(encoder.encode(form.getSenha()));
        Usuario estudante = form.converter();
        repository.save(estudante);
        URI uri = uriBuilder.path("/usuarios/estudantes/{id}").buildAndExpand(estudante.getId()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioEstudanteDto(estudante));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEstudanteDto>> read() {
        List<Usuario> list = repository.findAll();
        ListObj<Usuario> listObj = new ListObj<>(100);
        for (Usuario usuario : list) {
            if (usuario.getAreaAtuacao() == null && usuario.getInicioAtuacao() == null) {
                listObj.add(usuario);
            }
        }
        return listObj.getTamanho() == 0
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(UsuarioEstudanteDto.converter(listObj.all()));
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
