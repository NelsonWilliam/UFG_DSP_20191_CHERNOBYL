package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "filme")
public class FilmeEntity {

    @Id
    @GeneratedValue
    private long id;

    @NotBlank
    @Size(min = 2, max = 200)
    private String titulo;

    private long idDiretor;

    private List<Long> idAtores;

    private List<Long> idAtrizes;

    private float grauRadioatividade;

    private List<String> premiacoes;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getIdDiretor() {
        return idDiretor;
    }

    public void setIdDiretor(long idDiretor) {
        this.idDiretor = idDiretor;
    }

    public List<Long> getIdAtores() {
        return idAtores;
    }

    public void setIdAtores(List<Long> idAtores) {
        this.idAtores = idAtores;
    }

    public List<Long> getIdAtrizes() {
        return idAtrizes;
    }

    public void setIdAtrizes(List<Long> idAtrizes) {
        this.idAtrizes = idAtrizes;
    }

    public float getGrauRadioatividade() {
        return grauRadioatividade;
    }

    public void setGrauRadioatividade(float grauRadioatividade) {
        this.grauRadioatividade = grauRadioatividade;
    }

    public List<String> getPremiacoes() {
        return premiacoes;
    }

    public void setPremiacoes(List<String> premiacoes) {
        this.premiacoes = premiacoes;
    }
}
