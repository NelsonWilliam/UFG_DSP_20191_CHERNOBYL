package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(min = 2, max = 200)
    private String titulo;

    @ManyToOne(fetch = FetchType.LAZY)
    private Profissional diretor;

    @ManyToMany(fetch = FetchType.LAZY)
    private Collection<Profissional> atores;

    private Float grauRadioatividade;

    @ElementCollection
    private List<String> premiacoes;

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

    public Profissional getDiretor() {
        return diretor;
    }

    public void setDiretor(Profissional diretor) {
        this.diretor = diretor;
    }

    public Collection<Profissional> getAtores() {
        return atores;
    }

    public void setAtores(Collection<Profissional> atores) {
        this.atores = atores;
    }

    public Float getGrauRadioatividade() {
        return grauRadioatividade;
    }

    public void setGrauRadioatividade(Float grauRadioatividade) {
        this.grauRadioatividade = grauRadioatividade;
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

}
