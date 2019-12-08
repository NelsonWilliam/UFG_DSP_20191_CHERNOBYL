package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsável pela conversão entre a Entidade "Avaliação Filme" e um Data Transfer Object e vice-versa.
 */
@Component
public class AvaliacaoFilmeAssembler {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Converte um Data Transfer Object "Avaliação Filme" em uma Entidade "Avaliação Filme".
     *
     * @param avaliacaoFilmeDto Instância de AvaliaçãoFilmeDto.
     * @return Instância de AvaliacaoFilme.
     */
    public AvaliacaoFilme toEntity(AvaliacaoFilmeDto avaliacaoFilmeDto) {
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setId(avaliacaoFilmeDto.getId());
        aval.setUsuario(usuarioService.findEntityById(avaliacaoFilmeDto.getIdUsuario()));
        aval.setFilme(filmeService.findEntityById(avaliacaoFilmeDto.getIdFilme()));
        aval.setNota(avaliacaoFilmeDto.getNota());
        return aval;
    }

    /**
     * Converte uma Entidade "Avaliação Filme" em um Data Transfer Object "Avaliação Filme".
     *
     * @param avaliacaoFilme Instância de AvaliacaoFilme.
     * @return Instância de AvaliacaoFilmeDto.
     */
    public AvaliacaoFilmeDto toDto(AvaliacaoFilme avaliacaoFilme) {
        AvaliacaoFilmeDto dto = new AvaliacaoFilmeDto();
        dto.setId(avaliacaoFilme.getId());
        dto.setIdUsuario(avaliacaoFilme.getUsuario().getId());
        dto.setIdFilme(avaliacaoFilme.getFilme().getId());
        dto.setNota(avaliacaoFilme.getNota());
        return dto;
    }

}
