package br.com.nelsonwilliam.dsp20191.chernobyl.dtos;

import javax.validation.constraints.NotNull;

public class AvaliacaoResenhaDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idResenha;

    @NotNull
    private Boolean positiva;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdResenha() {
        return idResenha;
    }

    public void setIdResenha(Long idResenha) {
        this.idResenha = idResenha;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }

}
