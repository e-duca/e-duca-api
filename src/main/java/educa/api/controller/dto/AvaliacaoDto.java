package educa.api.controller.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
public class AvaliacaoDto {

    @Min(1)
    private int idConteudo;
    private int idUsuario;
    @Size(min = 3, max = 15)
    private String avaliacao;

}
