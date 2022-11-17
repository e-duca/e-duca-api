package educa.api.request;

import educa.api.request.domain.Habilidade;
import educa.api.request.domain.Usuario;
import lombok.Getter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class ConteudoRequest {

    @Size(min = 4)
    @NotBlank
    private String titulo;
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
