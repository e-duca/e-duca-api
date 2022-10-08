package educa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Resposta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//  @ManyToMany
//  private Topico topico;
//  @ManyToMany
//  private Estudante estudante;
    @Size(min = 3)
    private String resposta;

}