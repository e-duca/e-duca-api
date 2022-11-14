package educa.api.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_resposta")
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idResposta;
    @Size(min = 3, max = 5000)
    private String resposta;
    @CreationTimestamp
    private LocalDateTime dataCriacao;
    @ManyToOne
    @JsonIgnore
    private Topico topico;
    @ManyToOne
    private Usuario usuario;

}