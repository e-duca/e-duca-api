package educa.api.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conteudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idConteudo;
    @Size(min = 4)
    private String titulo;
    @Size(min = 4)
    private String tipo;
    @URL
    private String url;
    @NotNull
    private int quatCurtidas;
    @OneToMany
    private Professor professor;
    @OneToMany
    private Estudante estudante;
    @OneToMany
    private  Habilidade habilidade;

}