package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoTopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.TopicoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsável pela conversão entre a Entidade "Avaliação Tópico" e um Data Transfer Object e vice-versa.
 */
@Component
public class AvaliacaoTopicoAssembler {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Converte um Data Transfer Object "Avaliação Topico" em uma Entidade "Avaliação Topico".
     *
     * @param avaliacaoTopicoDto Instância de AvaliacaoTopicoDto.
     * @return Instância de AvaliacaoTopico.
     */
    public AvaliacaoTopico toEntity(AvaliacaoTopicoDto avaliacaoTopicoDto) {
        AvaliacaoTopico avaliacaoTopico = new AvaliacaoTopico();
        avaliacaoTopico.setPositiva(avaliacaoTopicoDto.isPositiva());
        avaliacaoTopico.setId(avaliacaoTopicoDto.getId());
        avaliacaoTopico.setTopico(topicoService.findEntityById(avaliacaoTopicoDto.getIdTopico()));
        avaliacaoTopico.setUsuario(usuarioService.findEntityById(avaliacaoTopicoDto.getIdUsuario()));
        return avaliacaoTopico;
    }

    /**
     * Converte uma Entidade "Avaliação Topico" em um Data Transfer Object "Avaliação Topico".
     *
     * @param avaliacaoTopico Instância de AvaliacaoTopico.
     * @return Instância de AvaliacaoTopicoDto.
     */
    public AvaliacaoTopicoDto toDto(AvaliacaoTopico avaliacaoTopico) {
        AvaliacaoTopicoDto dto = new AvaliacaoTopicoDto();
        dto.setId(avaliacaoTopico.getId());
        dto.setPositiva(avaliacaoTopico.isPositiva());
        dto.setIdTopico(avaliacaoTopico.getTopico().getId());
        dto.setIdUsuario(avaliacaoTopico.getUsuario().getId());
        return dto;
    }

}
