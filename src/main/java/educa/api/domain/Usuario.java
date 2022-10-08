package educa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;

@MappedSuperclass
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Size(min = 3)
    @NotBlank
    private String nome;
    @Size(min = 3)
    @NotBlank
    private String sobrenome;
    @Past
    @NotNull
    private LocalDate dataNasc;
    @Column(unique = true)
    @Email
    @NotBlank
    private String email;
    @Size(min = 8)
    @NotBlank
    private String senha;
    private boolean autenticado;

}
