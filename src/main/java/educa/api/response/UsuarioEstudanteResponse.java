package educa.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UsuarioEstudanteResponse {

    private int idUsuario;
    private String nome;
    private String sobrenome;
    private String email;

}
