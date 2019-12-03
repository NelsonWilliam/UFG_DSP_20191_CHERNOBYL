package br.com.nelsonwilliam.dsp20191.chernobyl.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TopicoDto {

    private Long id;

    private Long idAutor;

    private Long idFilme;

    @NotBlank
    @Size(min = 1, max = 100)
    private String texto;

    private Boolean minhaAvaliacao;

    private Double mediaAvaliacao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getMinhaAvaliacao() {
        return minhaAvaliacao;
    }

    public void setMinhaAvaliacao(Boolean minhaAvaliacao) {
        this.minhaAvaliacao = minhaAvaliacao;
    }

    public Long getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Long idAutor) {
        this.idAutor = idAutor;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Double mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(Long idFilme) {
        this.idFilme = idFilme;
    }
}
