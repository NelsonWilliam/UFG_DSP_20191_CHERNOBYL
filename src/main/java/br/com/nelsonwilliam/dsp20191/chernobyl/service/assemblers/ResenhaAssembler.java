package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.ResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AuthService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AvaliacaoResenhaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ResenhaAssembler {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoResenhaService avaliacaoResenhaService;

    @Autowired
    private AuthService authService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

    //Converte um Data Transfer Object "Resenha" em uma Entidade "Resenha"
    public Resenha toEntity(ResenhaDto dto) {
        Resenha resenha = new Resenha();
        resenha.setId(dto.getId());
        resenha.setFilme(filmeService.findEntityById(dto.getIdFilme()));
        resenha.setTexto(dto.getTexto());
        resenha.setAutor(usuarioService.findEntityById(dto.getIdAutor()));
        return resenha;
    }

    //Converte uma Entidade "Resenha" em um Data Transfer Object "Resenha"
    public ResenhaDto toDto(Resenha resenha) {
        ResenhaDto resenhaDto = new ResenhaDto();
        resenhaDto.setId(resenha.getId());
        resenhaDto.setIdAutor(resenha.getAutor().getId());
        resenhaDto.setIdFilme(resenha.getFilme().getId());
        resenhaDto.setTexto(resenha.getTexto());
        Long idUsuario = authService.getIdUsuario();
        AvaliacaoResenha minhaAvaliacao = avaliacaoResenhaService.findEntityByResenhaAndUsuario(resenha.getId(), idUsuario);
        resenhaDto.setMinhaAvaliacao(minhaAvaliacao == null ? null : minhaAvaliacao.isPositiva());
        resenhaDto.setMediaAvaliacao(avaliacaoService.calcularMediaAprovacao(resenha));
        return resenhaDto;
    }

}
