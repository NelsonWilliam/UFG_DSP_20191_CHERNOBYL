package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.TopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AuthService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AvaliacaoTopicoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Responsável pela conversão entre a Entidade "Tópico" e um Data Transfer Object e vice-versa.
 */
@Component
public class TopicoAssembler {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoTopicoService avaliacaoTopicoService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Converte um Data Transfer Object "Topico" em uma Entidade "Topico".
     *
     * @param topicoDto Instância de TopicoDto.
     * @return Instância de Topico.
     */
    public Topico toEntity(TopicoDto topicoDto) {
        Topico topico = new Topico();
        topico.setId(topicoDto.getId());
        topico.setFilme(filmeService.findEntityById(topicoDto.getIdFilme()));
        topico.setTexto(topicoDto.getTexto());
        topico.setAutor(usuarioService.findEntityById(topicoDto.getIdAutor()));
        return topico;
    }

    /**
     * Converte uma Entidade "Topico" em um Data Transfer Object "Topico".
     *
     * @param topico Instância de Topico.
     * @return Instância de TopicoDto.
     */
    public TopicoDto toDto(Topico topico) {
        TopicoDto topicoDto = new TopicoDto();
        topicoDto.setId(topico.getId());
        topicoDto.setIdAutor(topico.getAutor().getId());
        topicoDto.setIdFilme(topico.getFilme().getId());
        topicoDto.setTexto(topico.getTexto());
        Long idUsuario = authService.getIdUsuario();
        AvaliacaoTopico minhaAvaliacao = avaliacaoTopicoService.findEntityByTopicoAndUsuario(topico.getId(), idUsuario);
        topicoDto.setMinhaAvaliacao(minhaAvaliacao == null ? null : minhaAvaliacao.isPositiva());
        topicoDto.setMediaAvaliacao(avaliacaoService.calcularMediaAprovacao(topico));
        return topicoDto;
    }

}
