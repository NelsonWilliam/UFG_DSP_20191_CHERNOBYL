package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.PessoaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AuthService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.AvaliacaoFilmeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsável pela conversão entre a Entidade "Filme" e um Data Transfer Object e vice-versa.
 */
@Component
public class FilmeAssembler {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private ResenhaAssembler resenhaAssembler;

    @Autowired
    private TopicoAssembler topicoAssembler;

    /**
     * Converte um Data Transfer Object "Filme" em uma Entidade "Filme".
     *
     * @param filmeDto Instância de FilmeDto.
     * @return Instância de Filme.
     */
    public Filme toEntity(FilmeDto filmeDto) {
        Filme filme = new Filme();
        filme.setId(filmeDto.getId());
        filme.setTitulo(filmeDto.getTitulo());
        filme.setDiretor(pessoaRepository.findById(filmeDto.getIdDiretor()).orElse(null));
        List<Pessoa> atores = new ArrayList<>();
        if (filmeDto.getIdsAtores() != null)
            for (Long idAtor : filmeDto.getIdsAtores()) {
                atores.add(pessoaRepository.findById(idAtor).orElse(null));
            }
        filme.setAtores(atores);
        filme.setPremiacoes(filmeDto.getPremiacoes());
        filme.setImage(filmeDto.getImage());
        return filme;
    }

    /**
     * Converte uma Entidade "Filme" em um Data Transfer Object "Filme".
     *
     * @param filme Instância de Filme.
     * @return Instância de FilmeDto.
     */
    public FilmeDto toDto(Filme filme) {
        FilmeDto filmeDto = new FilmeDto();
        filmeDto.setId(filme.getId());
        filmeDto.setTitulo(filme.getTitulo());

        Pessoa diretor = filme.getDiretor();
        filmeDto.setIdDiretor(diretor == null ? null : diretor.getId());

        List<Long> idsAtores = new ArrayList<>();
        Collection<Pessoa> atores = filme.getAtores();
        for (Pessoa ator : atores) {
            idsAtores.add(ator.getId());
        }
        filmeDto.setIdsAtores(idsAtores);
        filmeDto.setPremiacoes(filme.getPremiacoes());
        filmeDto.setImage(filme.getImage());
        filmeDto.setResenhas(filme.getResenhas().stream().map(r -> resenhaAssembler.toDto(r)).collect(Collectors.toList()));
        filmeDto.setTopicos(filme.getTopicos().stream().map(t -> topicoAssembler.toDto(t)).collect(Collectors.toList()));

        Long idUsuario = authService.getIdUsuario();
        AvaliacaoFilmeDto minhaAvaliacao = idUsuario == null ? null : avaliacaoFilmeService.findByFilmeAndUsuario(filme.getId(), idUsuario);
        filmeDto.setMinhaAvaliacao(minhaAvaliacao == null ? null : minhaAvaliacao.getNota());
        filmeDto.setMediaAvaliacao(avaliacaoService.calcularMediaAvaliacao(filme));
        return filmeDto;
    }

}
