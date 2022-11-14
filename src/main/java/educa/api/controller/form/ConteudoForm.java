package educa.api.controller.form;

import educa.api.domain.Avaliacao;
import educa.api.domain.Habilidade;
import educa.api.domain.Usuario;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ConteudoForm {

    @Size(min = 4)
    @NotBlank
    private String titulo;
    @URL
    @NotBlank
    private String url;
    @Size(min = 4)
    private String artigo;
    @Size(min = 3, max = 5000)
    private String texto;
    @URL
    private String urlVideo;
    @ManyToOne
    private Usuario autor;
    @ManyToOne
    @NotNull
    private Habilidade habilidade;

}
