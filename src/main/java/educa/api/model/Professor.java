package educa.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Usuario {

    @NotBlank
    private String areaAtuacao;
    @NotNull
    private Integer tempoCarreira;

}
