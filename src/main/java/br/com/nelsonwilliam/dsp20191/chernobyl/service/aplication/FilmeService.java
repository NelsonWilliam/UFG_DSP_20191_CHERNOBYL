package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.FilmeRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure.ImageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Autowired
    private PessoaService pessoaService;

    public List<Filme> findAll() {
        return filmeRepository.findAll();
    }

    public Filme findById(Long id) {
        Optional<Filme> exemplo = filmeRepository.findById(id);
        return exemplo.orElse(null);
    }

    public FilmeDto findDtoById(Long id) {
        Optional<Filme> exemplo = filmeRepository.findById(id);
        if (exemplo == null) {
            return null;
        } else {
            return FilmeDto.fromFilme(exemplo.orElse(null));
        }
    }

    private Collection<Filme> findByDiretorPrivate(Long idDiretor) {
        return filmeRepository.findByDiretor_Id(idDiretor);
    }

    private Collection<Filme> findByAtorPrivate(Long idAtor) {
        return filmeRepository.findByAtores_Id(idAtor);
    }

    public Collection<FilmeDto> findDtosByAtor(Long idAtor) {
        Collection<Filme> filmes = filmeRepository.findByAtores_Id(idAtor);
        Collection<FilmeDto> filmesDto = new ArrayList<>();
        for(Filme filme: filmes){
            filmesDto.add(FilmeDto.fromFilme(filme));
        }
        return filmesDto;
    }

    public Collection<FilmeDto> findDtosByDiretor(Long idDiretor) {
        Collection<Filme> filmes = filmeRepository.findByDiretor_Id(idDiretor);
        Collection<FilmeDto> filmesDto = new ArrayList<>();
        for(Filme filme: filmes){
            filmesDto.add(FilmeDto.fromFilme(filme));
        }
        return filmesDto;
    }


    public List<Filme> findByPessoa(Long idPessoa) {
        Collection<Filme> porDiretor = findByDiretorPrivate(idPessoa);
        Collection<Filme> porAtor = findByAtorPrivate(idPessoa);
        return Stream.of(porDiretor, porAtor).flatMap(Collection::stream).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        filmeRepository.deleteById(id);
    }

    public Long save(FilmeDto filmeDto) {
        if (filmeDto.getImage() == null || filmeDto.getImage().isEmpty()) {
            filmeDto.setImage(ImageHandler.getFilmImgString());
        }

        Filme filme = filmeDto.toFilme(pessoaService);
        return filmeRepository.save(filme).getId();
    }

    public List<FilmeDto> findAllDto() {
        return findAll().stream().map(f -> {
            return FilmeDto.fromFilme(f);
        }).collect(Collectors.toList());
    }

    public Long alterarImagem(Long id, File f) throws Exception {
        Filme filme = findById(id);
        String img = ImageHandler.imageToString(f);
        filme.setImage(img);
        return filmeRepository.save(filme).getId();
    }
}
