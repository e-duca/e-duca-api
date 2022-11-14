package educa.api.controller;

import educa.api.domain.Perfil;
import educa.api.domain.Usuario;
import educa.api.controller.dto.UsuarioEstudanteDto;
import educa.api.controller.form.EstudanteForm;
import educa.api.repository.PerfilRepository;
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
    private PerfilRepository perfilRepository;
    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<UsuarioEstudanteDto> create(@RequestBody @Valid EstudanteForm form, UriComponentsBuilder uriBuilder) {
        form.setSenha(encoder.encode(form.getSenha()));
        Usuario estudante = form.converter();
        Perfil perfilEstudante = perfilRepository.findByNome("ESTUDANTE");
        estudante.adicionarPerfil(perfilEstudante);
        repository.save(estudante);
        URI uri = uriBuilder.path("/usuarios/estudantes/{id}").buildAndExpand(estudante.getIdUsuario()).toUri();
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
    public ResponseEntity<UsuarioEstudanteDto> read(@PathVariable int id) {
        Optional<Usuario> estudante = repository.findById(id);
        if (repository.existsById(id)) {
            if (estudante.get().getAreaAtuacao() == null && estudante.get().getInicioAtuacao() == null) {
                return ResponseEntity.status(200).body(new UsuarioEstudanteDto(estudante.get()));
            }
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(400).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioEstudanteDto> update(
            @PathVariable int id,
            @RequestBody @Valid Usuario estudante) {
        if (repository.existsById(id)) {
            estudante.setIdUsuario(id);
            Perfil perfilEstudante = perfilRepository.findByNome("ESTUDANTE");
            estudante.adicionarPerfil(perfilEstudante);
            repository.save(estudante);
            return ResponseEntity.status(200).body(new UsuarioEstudanteDto(estudante));
        }
        return ResponseEntity.status(404).build();
    }

}
