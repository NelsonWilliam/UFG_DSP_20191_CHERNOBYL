package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.PapelEnum;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @Size(min = 3, max = 32)
    private String login;

    @NotBlank
    @Size(min = 3)
    private String senha;

    private String nome;

    private String email;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<PapelEnum> papeis = new ArrayList<>();

    @OneToMany(mappedBy = "autor", orphanRemoval = true)
    private List<Resenha> resenhas = new ArrayList<>();

    @OneToMany(mappedBy = "autor", orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public List<PapelEnum> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<PapelEnum> papeis) {
        this.papeis = papeis;
    }
}
