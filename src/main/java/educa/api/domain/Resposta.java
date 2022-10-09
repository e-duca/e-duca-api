package educa.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Size(min = 3, max = 5000)
    private String resposta;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @ManyToOne
    @JsonIgnore
    private Topico topico;
    @ManyToOne
    private Estudante autor;

}