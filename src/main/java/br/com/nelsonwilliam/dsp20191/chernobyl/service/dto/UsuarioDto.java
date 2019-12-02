package br.com.nelsonwilliam.dsp20191.chernobyl.service.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;

public class UsuarioDto {

    private Long id;

    @NotNull
    @Size(min = 3, max = 32)
    private String login;

    @NotNull
    @Size(min = 3, max = 32)
    private String senha;

    @NotBlank
    private String nome;

    @NotBlank
    private String email;

    public Usuario toUsuario(PasswordEncoder passwordEncoder) {
        Usuario usuario = new Usuario();
        usuario.setLogin(getLogin());
        usuario.setSenha(passwordEncoder.encode(getSenha()));
        usuario.setNome(getNome());
        usuario.setEmail(getEmail());
        usuario.setPapeis(Arrays.asList(PapelEnum.USUARIO));
        return usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
