package educa.api.domain.dto;

import educa.api.domain.Usuario;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class UsuarioProfessorDto {

    private Integer id;
    private String nome;
    private String sobrenome;
    private LocalDate dataNasc;
    private String email;
    private String areaAtuacao;
    private LocalDate inicioAtuacao;

    public UsuarioProfessorDto(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.sobrenome = usuario.getSobrenome();
        this.dataNasc = usuario.getDataNasc();
        this.email = usuario.getEmail();
        this.areaAtuacao = usuario.getAreaAtuacao();
        this.inicioAtuacao = usuario.getInicioAtuacao();
    }

    public static List<UsuarioProfessorDto> converter(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioProfessorDto::new).collect(Collectors.toList());
    }

}
