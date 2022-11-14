package educa.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class UsuarioProfessorResponse {

    private int idUsuario;
    private String nome;
    private String sobrenome;
    private String email;
    private String areaAtuacao;
    private LocalDate inicioAtuacao;

}
