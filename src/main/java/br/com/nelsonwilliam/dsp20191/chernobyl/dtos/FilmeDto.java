package br.com.nelsonwilliam.dsp20191.chernobyl.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class FilmeDto {

    private Long id;

    @NotBlank
    @Size(min = 2, max = 128)
    private String titulo;

    @NotNull
    private Long idDiretor;

    private List<Long> idsAtores = new ArrayList<>();

    private List<String> premiacoes = new ArrayList<>();

    private String image;

    private List<ResenhaDto> resenhas = new ArrayList<>();

    private List<TopicoDto> topicos = new ArrayList<>();

    private Integer minhaAvaliacao = -1;

    private Double mediaAvaliacao;

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

    public Long getIdDiretor() {
        return idDiretor;
    }

    public void setIdDiretor(Long idDiretor) {
        this.idDiretor = idDiretor;
    }

    public List<Long> getIdsAtores() {
        return idsAtores;
    }

    public void setIdsAtores(List<Long> idsAtores) {
        this.idsAtores = idsAtores;
    }

    public List<String> getPremiacoes() {
        return premiacoes;
    }

    public void setPremiacoes(List<String> premiacoes) {
        this.premiacoes = premiacoes;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public List<ResenhaDto> getResenhas() {
        return resenhas;
    }

    public void setResenhas(List<ResenhaDto> resenhas) {
        this.resenhas = resenhas;
    }

    public List<TopicoDto> getTopicos() {
        return topicos;
    }

    public void setTopicos(List<TopicoDto> topicos) {
        this.topicos = topicos;
    }

    public Integer getMinhaAvaliacao() {
        return minhaAvaliacao;
    }

    public void setMinhaAvaliacao(Integer minhaAvaliacao) {
        this.minhaAvaliacao = minhaAvaliacao;
    }
}
