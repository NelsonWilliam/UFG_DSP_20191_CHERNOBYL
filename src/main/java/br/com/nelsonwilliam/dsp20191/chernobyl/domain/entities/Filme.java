package br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String titulo;

    @ElementCollection
    private List<String> premiacoes;

    @Lob
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa diretor;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pessoa> atores = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme", orphanRemoval = true)
    private List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme", orphanRemoval = true)
    private List<Resenha> resenhas = new ArrayList<>();

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
