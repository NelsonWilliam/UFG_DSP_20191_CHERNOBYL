package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representa um Filme a ser mantido pelo sistema.
 */
@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String titulo;

    /**
     * Premiações às quais o filme venceu.
     */
    @ElementCollection
    private List<String> premiacoes;

    /**
     * Foto a representar o filme, devendo estar na base 64.
     */
    @Lob
    private String image;

    /**
     * Pessoa que dirigiu o filme.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa diretor;

    /**
     * Pessoas que atuaram no filme.
     */
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pessoa> atores = new ArrayList<>();

    /**
     * Avaliações do filme enviadas por usuários.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme", orphanRemoval = true)
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

    /**
     * Resenhas do filme postadas por usuário.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme", orphanRemoval = true)
    private List<Resenha> resenhas = new ArrayList<>();

    /**
     * Tópicos de radioatividade postados pelos usuários.
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme", orphanRemoval = true)
    private List<Topico> topicos = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Pessoa getDiretor() {
        return diretor;
    }

    public void setDiretor(Pessoa diretor) {
        this.diretor = diretor;
    }

    public Collection<Pessoa> getAtores() {
        return atores;
    }

    public void setAtores(List<Pessoa> atores) {
        this.atores = atores;
    }

    public List<String> getPremiacoes() {
        return premiacoes;
    }

    public void setPremiacoes(List<String> premiacoes) {
        this.premiacoes = premiacoes;
    }

    public List<Resenha> getResenhas() {
        return resenhas;
    }

    public void setResenhas(List<Resenha> resenhas) {
        this.resenhas = resenhas;
    }

    public List<Topico> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<Topico> topicos) {
        this.topicos = topicos;
    }

    public List<AvaliacaoFilme> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<AvaliacaoFilme> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
