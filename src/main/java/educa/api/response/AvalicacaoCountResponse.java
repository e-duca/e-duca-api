package educa.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvalicacaoCountResponse {

    private String tipoAvaliacao;
    private Long quantidade;

}
