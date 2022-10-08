package educa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Professor extends Usuario {

    @NotBlank
    private String areaAtuacao;
    @NotNull
    private LocalDate inicioAtuazao;

}
