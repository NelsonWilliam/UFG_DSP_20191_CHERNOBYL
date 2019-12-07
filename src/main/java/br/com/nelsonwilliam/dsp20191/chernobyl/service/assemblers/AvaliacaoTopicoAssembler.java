package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoTopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.TopicoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoTopicoAssembler {

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private UsuarioService usuarioService;

    //Converte um Data Transfer Object "Avaliação Topico" em uma Entidade "Avaliação Topico"
    public AvaliacaoTopico toEntity(AvaliacaoTopicoDto dto) {
        AvaliacaoTopico avaliacaoTopico = new AvaliacaoTopico();
        avaliacaoTopico.setPositiva(dto.isPositiva());
        avaliacaoTopico.setId(dto.getId());
        avaliacaoTopico.setTopico(topicoService.findEntityById(dto.getIdTopico()));
        avaliacaoTopico.setUsuario(usuarioService.findEntityById(dto.getIdUsuario()));
        return avaliacaoTopico;
    }

    //Converte uma Entidade "Avaliação Topico" em um Data Transfer Object "Avaliação Topico"
    public AvaliacaoTopicoDto toDto(AvaliacaoTopico avaliacaoTopico) {
        AvaliacaoTopicoDto dto = new AvaliacaoTopicoDto();
        dto.setId(avaliacaoTopico.getId());
        dto.setPositiva(avaliacaoTopico.isPositiva());
        dto.setIdTopico(avaliacaoTopico.getTopico().getId());
        dto.setIdUsuario(avaliacaoTopico.getUsuario().getId());
        return dto;
    }

}
