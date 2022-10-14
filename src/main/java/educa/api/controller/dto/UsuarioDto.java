package educa.api.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private String email;
    private String senha;

    public UsernamePasswordAuthenticationToken converter() {
        return new UsernamePasswordAuthenticationToken(email, senha);
    }
}
