package educa.api.controller.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
public class RespostaDto {

    @Min(1)
    private int idTopico;
    private int idUsuario;
    @Size(min = 3, max = 5000)
    private String resposta;

}
