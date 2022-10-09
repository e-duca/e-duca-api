package educa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTopico;
    @Size(min = 5)
    private String titulo;
    @Size(min = 5)
    private String descricao;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @Enumerated(EnumType.STRING)
    private StatusTopico status = StatusTopico.NAO_RESPONDIDO;
    @ManyToOne
    private Estudante autor;
    @OneToMany(mappedBy = "topico")
    private List<Resposta> respostas = new ArrayList<>();

}

