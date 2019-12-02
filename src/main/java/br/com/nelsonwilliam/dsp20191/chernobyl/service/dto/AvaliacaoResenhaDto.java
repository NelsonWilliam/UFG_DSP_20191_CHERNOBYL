package br.com.nelsonwilliam.dsp20191.chernobyl.service.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.ResenhaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.UsuarioService;

import javax.validation.constraints.NotNull;

public class AvaliacaoResenhaDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idResenha;

    @NotNull
    private boolean positiva;

    public static AvaliacaoResenhaDto fromAvaliacaoResenha(AvaliacaoResenha avaliacaoResenha){
        AvaliacaoResenhaDto dto = new AvaliacaoResenhaDto();
        dto.setId(avaliacaoResenha.getId());
        dto.setIdResenha(avaliacaoResenha.getResenha().getId());
        dto.setIdUsuario(avaliacaoResenha.getUsuario().getId());
        dto.setPositiva(avaliacaoResenha.isPositiva());

        return dto;
    }

    public static AvaliacaoResenha toAvaliacaoResenha(AvaliacaoResenhaDto dto, ResenhaService resenhaService, UsuarioService usuarioService){
        AvaliacaoResenha avaliacaoResenha = new AvaliacaoResenha();
        avaliacaoResenha.setId(dto.getId());
        avaliacaoResenha.setPositiva(dto.isPositiva());
        avaliacaoResenha.setResenha(resenhaService.findById(dto.getIdResenha()));
        avaliacaoResenha.setUsuario(usuarioService.findById(dto.getId()));

        return avaliacaoResenha;

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
