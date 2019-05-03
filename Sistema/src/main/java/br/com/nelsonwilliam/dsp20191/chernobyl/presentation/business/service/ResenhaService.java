package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.ResenhaEntity;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class ResenhaService {

    @Autowired
    private ResenhaRepository resenhaRepository;

    public List<ResenhaEntity> findAll() {
        return resenhaRepository.findAll();
    }

    public void deleteById(long id) {
        resenhaRepository.deleteById(id);
    }

    public void save(ResenhaEntity resenhaEntity) {
        resenhaRepository.save(resenhaEntity);
    }
}
