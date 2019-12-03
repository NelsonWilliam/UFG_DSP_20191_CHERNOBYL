package br.com.nelsonwilliam.dsp20191.chernobyl.dtos;

import javax.validation.constraints.NotNull;

public class AvaliacaoTopicoDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idTopico;

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

    public Long getIdTopico() {
        return idTopico;
    }

    public void setIdTopico(Long idTopico) {
        this.idTopico = idTopico;
    }

    public boolean isPositiva() {
        return positiva;
    }

    public void setPositiva(boolean positiva) {
        this.positiva = positiva;
    }
}
