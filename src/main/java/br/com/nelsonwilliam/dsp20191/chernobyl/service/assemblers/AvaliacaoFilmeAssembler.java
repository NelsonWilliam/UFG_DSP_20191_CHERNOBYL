package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoFilmeAssembler {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    //Converte um Data Transfer Object "Avaliação Filme" em uma Entidade "Avaliação Filme"
    public AvaliacaoFilme toEntity(AvaliacaoFilmeDto dto) {
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setId(dto.getId());
        aval.setUsuario(usuarioService.findEntityById(dto.getIdUsuario()));
        aval.setFilme(filmeService.findEntityById(dto.getIdFilme()));
        aval.setNota(dto.getNota());
        return aval;
    }

    //Converte uma Entidade "Avaliação Filme" em um Data Transfer Object "Avaliação Filme"
    public AvaliacaoFilmeDto toDto(AvaliacaoFilme aval) {
        AvaliacaoFilmeDto dto = new AvaliacaoFilmeDto();
        dto.setId(aval.getId());
        dto.setIdUsuario(aval.getUsuario().getId());
        dto.setIdFilme(aval.getFilme().getId());
        dto.setNota(aval.getNota());
        return dto;
    }

}
