package educa.api.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

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
    private String areaAtuacao;
    private LocalDate inicioAtuacao;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfil> perfils= new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.perfils;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
