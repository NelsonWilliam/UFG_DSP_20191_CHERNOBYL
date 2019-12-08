package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma resenha de um filme postada por um usuário.
 */
@Entity
@Table(name = "resenha")
public class Resenha {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * A resenha propriamente dita.
     */
    @NotBlank
    @Size(min = 1, max = 500)
    private String texto;

    /**
     * Usuário autor da resenha.
     */
    @NotNull
    @ManyToOne
    private Usuario autor;

    /**
     * Filme a receber a resenha em questão.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;

    /**
     * Avaliações de usuários a respeito da resenha.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "resenha", orphanRemoval = true)
    private List<AvaliacaoResenha> avaliacoes = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public List<AvaliacaoResenha> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoResenha> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
