package br.com.nelsonwilliam.dsp20191.chernobyl.service.dto;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.TopicoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.UsuarioService;

import javax.validation.constraints.NotNull;

public class AvaliacaoTopicoDto {

    private Long id;

    @NotNull
    private Long idUsuario;

    @NotNull
    private Long idTopico;

    @NotNull
    private boolean positiva;

    public static AvaliacaoTopicoDto fromAvaliacaoTopico (AvaliacaoTopico avaliacaoTopico){
        AvaliacaoTopicoDto dto = new AvaliacaoTopicoDto();

        dto.setId(avaliacaoTopico.getId());
        dto.setPositiva(avaliacaoTopico.isPositiva());
        dto.setIdTopico(avaliacaoTopico.getTopico().getId());
        dto.setIdUsuario(avaliacaoTopico.getUsuario().getId());

        return dto;
    }

    public static AvaliacaoTopico toAvaliacaoTopico (AvaliacaoTopicoDto dto, TopicoService topicoService, UsuarioService usuarioService){
        AvaliacaoTopico avaliacaoTopico = new AvaliacaoTopico();

        avaliacaoTopico.setPositiva(dto.isPositiva());
        avaliacaoTopico.setId(dto.getId());
        avaliacaoTopico.setTopico(topicoService.findById(dto.getIdTopico()));
        avaliacaoTopico.setUsuario(usuarioService.findById(dto.id));

        return avaliacaoTopico;
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
