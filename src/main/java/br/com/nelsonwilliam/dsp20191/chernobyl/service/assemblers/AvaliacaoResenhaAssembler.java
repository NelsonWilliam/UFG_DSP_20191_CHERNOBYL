package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.ResenhaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsável pela conversão entre a Entidade "Avaliação Resenha" e um Data Transfer Object e vice-versa.
 */
@Component
public class AvaliacaoResenhaAssembler {

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Converte um Data Transfer Object "Avaliação Resenha" em uma Entidade "Avaliação Resenha".
     *
     * @param avaliacaoResenhaDto Instância de AvaliacaoResenhaDto.
     * @return Instância de AvaliacaoResenha.
     */
    public AvaliacaoResenha toEntity(AvaliacaoResenhaDto avaliacaoResenhaDto) {
        AvaliacaoResenha avaliacaoResenha = new AvaliacaoResenha();
        avaliacaoResenha.setId(avaliacaoResenhaDto.getId());
        avaliacaoResenha.setPositiva(avaliacaoResenhaDto.isPositiva());
        avaliacaoResenha.setResenha(resenhaService.findEntityById(avaliacaoResenhaDto.getIdResenha()));
        avaliacaoResenha.setUsuario(usuarioService.findEntityById(avaliacaoResenhaDto.getIdUsuario()));
        return avaliacaoResenha;
    }

    /**
     * Converte uma Entidade "Avaliação Resenha" em um Data Transfer Object "Avaliação Resenha".
     *
     * @param avaliacaoResenha Instância de AvaliacaoResenha.
     * @return Instância de AvaliacaoResenhaDto.
     */
    public AvaliacaoResenhaDto toDto(AvaliacaoResenha avaliacaoResenha) {
        AvaliacaoResenhaDto dto = new AvaliacaoResenhaDto();
        dto.setId(avaliacaoResenha.getId());
        dto.setIdResenha(avaliacaoResenha.getResenha().getId());
        dto.setIdUsuario(avaliacaoResenha.getUsuario().getId());
        dto.setPositiva(avaliacaoResenha.isPositiva());
        return dto;
    }

}
