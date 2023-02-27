package educa.api.request;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Getter
public class AvaliacaoRequest {

    @Min(1)
    private int idConteudo;
    private int idUsuario;
    @Size(min = 3, max = 20)
    private String avaliacao;

}
