package br.com.nelsonwilliam.dsp20191.chernobyl.dtos;

import javax.validation.constraints.NotNull;

public class AvaliacaoFilmeDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idFilme;

    private Integer nota = 0;

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

    public long getIdFilme() {
        return idFilme;
    }

    public void setIdFilme(long idFilme) {
        this.idFilme = idFilme;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

}
