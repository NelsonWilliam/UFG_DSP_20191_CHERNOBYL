package br.com.nelsonwilliam.dsp20191.chernobyl.web.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.UsuarioService;

import javax.validation.constraints.NotNull;

public class AvaliacaoFilmeDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private long idFilme;

    private int grauRadiacao = 0;

    public static AvaliacaoFilme toAvaliacaoFilme(AvaliacaoFilmeDto dto, FilmeService filmeService, UsuarioService usuarioService) {
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setFilme(filmeService.findById(dto.getId()));
        aval.setUsuario(usuarioService.findById(dto.idUsuario));
        aval.setGrauRadiacao(dto.grauRadiacao);
        aval.setId(dto.getId());

        return aval;
    }

    public static AvaliacaoFilmeDto fromAvaliacaoFilme(AvaliacaoFilme aval) {
        AvaliacaoFilmeDto dto = new AvaliacaoFilmeDto();
        dto.setId(aval.getId());
        dto.setIdFilme(aval.getFilme().getId());
        dto.setIdUsuario(aval.getUsuario().getId());
        dto.setGrauRadiacao(aval.getGrauRadiacao());

        return dto;
    }

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

    public int getGrauRadiacao() {
        return grauRadiacao;
    }

    public void setGrauRadiacao(int grauRadiacao) {
        this.grauRadiacao = grauRadiacao;
    }
}
