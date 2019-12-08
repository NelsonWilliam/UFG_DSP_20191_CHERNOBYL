package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um tópico de radioatividade postado por um usuário, na página de um filme.
 */
@Entity
@Table(name = "topico")
public class Topico {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * O tópico de radioatividade, propriamente dito.
     */
    @NotBlank
    @Size(min = 1, max = 100)
    private String texto;

    /**
     * Usuário autor do tópico de radioatividade.
     */
    @NotNull
    @ManyToOne
    private Usuario autor;

    /**
     * Filme a receber o tópico de radioatividade em questão.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Filme filme;

    /**
     * Avaliações de usuários a respeito do tópico.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "topico", orphanRemoval = true)
    private List<AvaliacaoTopico> avaliacoes = new ArrayList<>();

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

    public List<AvaliacaoTopico> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoTopico> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
}
