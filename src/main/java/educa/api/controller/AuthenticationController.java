package educa.api.controller;

import educa.api.config.security.TokenService;
import educa.api.repository.UsuarioRepository;
import educa.api.request.domain.Usuario;
import educa.api.response.TokenResponse;
import educa.api.request.UsuarioLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioRepository repository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginRequest usuarioLoginDto) {
        UsernamePasswordAuthenticationToken dadosLogin = usuarioLoginDto.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.status(200).body(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException err) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Usuário e/ou senha inválidos"
            );
        }
    }

    @GetMapping("/usuario-secao")
    public ResponseEntity<Usuario> read(@AuthenticationPrincipal Usuario usuarioToken) {
        Optional<Usuario> usuario = repository.findById(usuarioToken.getIdUsuario());
        return ResponseEntity.status(200).body(usuario.get());
    }
}
