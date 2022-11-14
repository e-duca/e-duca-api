package educa.api.controller;

import educa.api.domain.Perfil;
import educa.api.domain.Usuario;
import educa.api.controller.dto.UsuarioProfessorDto;
import educa.api.controller.form.ProfessorForm;
import educa.api.repository.PerfilRepository;
import educa.api.repository.UsuarioRepository;
import educa.api.utils.ListObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios/professores")
public class ProfessorController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private PasswordEncoder encoder;

    @PostMapping
    public ResponseEntity<UsuarioProfessorDto> create(@RequestBody @Valid ProfessorForm form, UriComponentsBuilder uriBuilder) {
        form.setSenha(encoder.encode(form.getSenha()));
        Usuario professor = form.converter();
        Perfil perfilProfessor = perfilRepository.findByNome("ROLE_PROFESSOR");
        professor.adicionarPerfil(perfilProfessor);
        repository.save(professor);
        URI uri = uriBuilder.path("/usuarios/professor/{id}").buildAndExpand(professor.getIdUsuario()).toUri();
        return ResponseEntity.created(uri).body(new UsuarioProfessorDto(professor));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioProfessorDto>> read() {
        List<Usuario> list = repository.findAll();
        ListObj<Usuario> listObj = new ListObj<>(100);
        for (Usuario usuario : list) {
            if (!(usuario.getAreaAtuacao() == null && usuario.getInicioAtuacao() == null)) {
                listObj.add(usuario);
            }
        }
        return listObj.getTamanho() == 0
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(UsuarioProfessorDto.converter(listObj.all()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioProfessorDto> read(@PathVariable int id) {
        Optional<Usuario> professor = repository.findById(id);
        if (repository.existsById(id)) {
            if (!(professor.get().getAreaAtuacao() == null && professor.get().getInicioAtuacao() == null)) {
                return ResponseEntity.status(200).body(new UsuarioProfessorDto(professor.get()));
            }
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(400).build();
    }

    @PutMapping
    public ResponseEntity<Usuario> updateProfessor(
            @RequestBody @Valid Usuario professor,
            @AuthenticationPrincipal Usuario usuario) {
        if (repository.existsById(usuario.getIdUsuario())) {
            professor.setIdUsuario(usuario.getIdUsuario());
            professor.setSenha(encoder.encode(professor.getSenha()));
            Perfil perfilProfessor = perfilRepository.findByNome("ROLE_PROFESSOR");
            professor.adicionarPerfil(perfilProfessor);
            repository.save(professor);
            return ResponseEntity.status(200).body(professor);
        }
        return ResponseEntity.status(404).build();
    }

}
