package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class ResenhaService {

    @Autowired
    private ResenhaRepository resenhaRepository;

    public List<Resenha> findAll() {
        return resenhaRepository.findAll();
    }

    public void deleteById(Long id) {
        resenhaRepository.deleteById(id);
    }

    public void save(Resenha resenha) {
        resenhaRepository.save(resenha);
    }
}
