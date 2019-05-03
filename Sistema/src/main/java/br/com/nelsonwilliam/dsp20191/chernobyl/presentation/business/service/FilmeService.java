package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.FilmeEntity;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class FilmeService {

    @Autowired
    private FilmeRepository filmeRepository;

    public List<FilmeEntity> findAll() {
        return filmeRepository.findAll();
    }

    public FilmeEntity findByAtor(long idAtor) {
        List<FilmeEntity> filmes = filmeRepository.findByAtor(idAtor);
        return filmes.size() > 0 ? filmes.get(0) : null;
    }

    public FilmeEntity findByAtriz(long idAtriz) {
        List<FilmeEntity> filmes = filmeRepository.findByAtriz(idAtriz);
        return filmes.size() > 0 ? filmes.get(0) : null;
    }

    public void deleteById(long id) {
        filmeRepository.deleteById(id);
    }

    public void save(FilmeEntity filmeEntity) {
        filmeRepository.save(filmeEntity);
    }

}
