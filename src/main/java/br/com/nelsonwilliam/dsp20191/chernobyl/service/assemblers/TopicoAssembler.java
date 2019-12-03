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

    public Topico toEntity(TopicoDto dto) {
        Topico topico = new Topico();
        topico.setId(dto.getId());
        topico.setFilme(filmeService.findEntityById(dto.getIdFilme()));
        topico.setTexto(dto.getTexto());
        topico.setAutor(usuarioService.findEntityById(dto.getIdAutor()));
        return topico;
    }

}
