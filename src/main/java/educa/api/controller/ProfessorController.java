package educa.api.controller;

import educa.api.request.domain.Perfil;
import educa.api.request.domain.Usuario;
import educa.api.request.ProfessorRequest;
import educa.api.repository.PerfilRepository;
import educa.api.repository.UsuarioRepository;
import educa.api.response.UsuarioProfessorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public ResponseEntity<UsuarioProfessorResponse> create(@RequestBody @Valid ProfessorRequest professorRequest) {

        if (repository.existsByEmail(professorRequest.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado na base de dados."
            );
        }

        professorRequest.setSenha(encoder.encode(professorRequest.getSenha()));
        Usuario professor = professorRequest.converter();
        Perfil perfilProfessor = perfilRepository.findByNome("ROLE_PROFESSOR");
        professor.adicionarPerfil(perfilProfessor);
        repository.save(professor);
        return ResponseEntity.status(201).body(repository.getProfessorById(professor.getIdUsuario()).get());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioProfessorResponse>> read() {
        List<UsuarioProfessorResponse> list = repository.getProfessores();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);
    }

    @GetMapping("/usuario-secao")
    public ResponseEntity<UsuarioProfessorResponse> read(@AuthenticationPrincipal Usuario usuario) {
        Optional<UsuarioProfessorResponse> professor = repository.getProfessorById(usuario.getIdUsuario());
        return professor.isPresent()
                ? ResponseEntity.status(200).body(professor.get())
                : ResponseEntity.status(400).build();
    }

    @PutMapping
    public ResponseEntity<UsuarioProfessorResponse> updateProfessor(
            @RequestBody @Valid Usuario professor,
            @AuthenticationPrincipal Usuario usuario) {
            professor.setIdUsuario(usuario.getIdUsuario());
            professor.setSenha(encoder.encode(professor.getSenha()));
            Perfil perfilProfessor = perfilRepository.findByNome("ROLE_PROFESSOR");
            professor.adicionarPerfil(perfilProfessor);
            repository.save(professor);
            return ResponseEntity.status(200).body(repository.getProfessorById(usuario.getIdUsuario()).get());
    }

}
