package educa.api.controller;

import educa.api.request.domain.Perfil;
import educa.api.request.domain.Usuario;
import educa.api.request.EstudanteRequest;
import educa.api.repository.PerfilRepository;
import educa.api.repository.UsuarioRepository;
import educa.api.response.UsuarioEstudanteResponse;
import educa.api.utils.FilaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
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
    public ResponseEntity<UsuarioEstudanteResponse> create(@RequestBody @Valid EstudanteRequest estudanteRequest) {
        if (repository.existsByEmail(estudanteRequest.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "E-mail já cadastrado na base de dados."
            );
        }
        estudanteRequest.setSenha(encoder.encode(estudanteRequest.getSenha()));
        Usuario estudante = estudanteRequest.converter();
        Perfil perfilEstudante = perfilRepository.findByNome("ROLE_ESTUDANTE");
        estudante.adicionarPerfil(perfilEstudante);
        repository.save(estudante);
        return ResponseEntity.status(201).body(repository.getEstudanteById(estudante.getIdUsuario()).get());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioEstudanteResponse>> read() {
        List<UsuarioEstudanteResponse> list = repository.getEstudantes();
        return list.isEmpty()
                ? ResponseEntity.status(204).build()
                : ResponseEntity.status(200).body(list);

    }

    @GetMapping("/usuario-secao")
    public ResponseEntity<UsuarioEstudanteResponse> read(@AuthenticationPrincipal Usuario usuario) {
        Optional<UsuarioEstudanteResponse> estudante = repository.getEstudanteById(usuario.getIdUsuario());
        return estudante.isPresent()
                ? ResponseEntity.status(200).body(estudante.get())
                : ResponseEntity.status(400).build();
    }

    @PutMapping
    public ResponseEntity<UsuarioEstudanteResponse> updateEstudante(
            @RequestBody @Valid Usuario estudante,
            @AuthenticationPrincipal Usuario usuario) {
            estudante.setIdUsuario(usuario.getIdUsuario());
            estudante.setSenha(encoder.encode(estudante.getSenha()));
            Perfil perfilEstudante = perfilRepository.findByNome("ROLE_ESTUDANTE");
            estudante.adicionarPerfil(perfilEstudante);
            repository.save(estudante);
            return ResponseEntity.status(200).body(repository.getEstudanteById(usuario.getIdUsuario()).get());
    }

    private FilaObj<Usuario> filaAgendar;

    @DeleteMapping()
    public ResponseEntity<Void> delete(@AuthenticationPrincipal Usuario usuario) {
        filaAgendar.insert(usuario);
        throw new ResponseStatusException(
                HttpStatus.OK,
                "Processo de exclusão da conta em andamento, prazo de até 24h"
        );
    }

    @Scheduled(cron = "0 0 24")
    public void deletarAccount() {
        for (int i = 0; i < filaAgendar.getTamanho(); i++) {
            repository.deleteById(filaAgendar.pool().getIdUsuario());
        }
    }

}
