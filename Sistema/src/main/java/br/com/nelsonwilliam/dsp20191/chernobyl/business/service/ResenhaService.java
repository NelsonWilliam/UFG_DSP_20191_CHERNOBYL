package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
