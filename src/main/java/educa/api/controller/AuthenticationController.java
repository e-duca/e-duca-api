package educa.api.controller;

import educa.api.config.security.TokenService;
import educa.api.response.TokenResponse;
import educa.api.request.UsuarioLoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginRequest usuarioLoginDto) {
        UsernamePasswordAuthenticationToken dadosLogin = usuarioLoginDto.converter();
        try {
            Authentication authentication = authenticationManager.authenticate(dadosLogin);
            String token = tokenService.gerarToken(authentication);
            return ResponseEntity.status(200).body(new TokenResponse(token, "Bearer"));
        } catch (AuthenticationException err) {
            return ResponseEntity.status(400).build();
        }
    }
}
