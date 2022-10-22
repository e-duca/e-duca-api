package educa.api.controller.dto;

import educa.api.domain.Usuario;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsuarioEstudanteDto {

    private Integer id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNasc;
    private String email;

    public UsuarioEstudanteDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.dataNasc = usuario.getDataNasc();
        this.email = usuario.getEmail();
    }

    public static List<UsuarioEstudanteDto> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioEstudanteDto::new).collect(Collectors.toList());
    }

}
