package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.ResenhaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoResenhaAssembler {

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private UsuarioService usuarioService;

    //Converte um Data Transfer Object "Avaliação Resenha" em uma Entidade "Avaliação Resenha"
    public AvaliacaoResenha toEntity(AvaliacaoResenhaDto dto) {
        AvaliacaoResenha avaliacaoResenha = new AvaliacaoResenha();
        avaliacaoResenha.setId(dto.getId());
        avaliacaoResenha.setPositiva(dto.isPositiva());
        avaliacaoResenha.setResenha(resenhaService.findEntityById(dto.getIdResenha()));
        avaliacaoResenha.setUsuario(usuarioService.findEntityById(dto.getIdUsuario()));
        return avaliacaoResenha;
    }

    //Converte uma Entidade "Avaliação Resenha" em um Data Transfer Object "Avaliação Resenha"
    public AvaliacaoResenhaDto toDto(AvaliacaoResenha avaliacaoResenha) {
        AvaliacaoResenhaDto dto = new AvaliacaoResenhaDto();
        dto.setId(avaliacaoResenha.getId());
        dto.setIdResenha(avaliacaoResenha.getResenha().getId());
        dto.setIdUsuario(avaliacaoResenha.getUsuario().getId());
        dto.setPositiva(avaliacaoResenha.isPositiva());
        return dto;
    }

}
