package br.com.nelsonwilliam.dsp20191.chernobyl.business.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @ManyToOne(fetch = FetchType.LAZY)
    private Pessoa diretor;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Pessoa> atores;

    @ElementCollection
    private List<String> premiacoes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme")
    private List<AvaliacaoFilme> avaliacoes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme")
    private List<Resenha> resenhas;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "filme")
    private List<Topico> topicos;

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
}
