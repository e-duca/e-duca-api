package educa.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
public class Pergunta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @ManyToMany
//    private Professor professor;
//    @ManyToMany
//    private Quiz quiz;
    @Size(min = 5)
    private String pergunta;
    @Size(min = 2)
    private String opcao1;
    @Size(min = 2)
    private String opcao2;
    @Size(min = 2)
    private String opcao3;
    @Size(min = 2)
    private String opcao4;
    @NotNull
    private boolean respostaCorreta;

}