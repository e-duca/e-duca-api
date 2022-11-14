package educa.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAvaliacao;
    @NotBlank
    @Size(min = 5)
    private String avaliacao;
    @ManyToOne
    @JsonIgnore
    private Conteudo conteudo;
    @ManyToOne
    private Usuario autor;

}
