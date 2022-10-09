package educa.api.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Habilidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHabilidade;
    @Size(min = 3)
    @NotBlank
    private String codigo;
    @NotBlank
    @Size(max = 500)
    private String descricao;
    @ManyToOne
    @JsonIgnore
    private Conteudo conteudo;

}
