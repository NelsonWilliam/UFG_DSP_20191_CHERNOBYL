package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.ResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    public Resenha save(Resenha resenha) {
        return resenhaRepository.save(resenha);
    }

    public Resenha findById(Long id) {
        Optional<Resenha> resenha = resenhaRepository.findById(id);
        return resenha.orElse(null);
    }

    public boolean resenhaExists(Resenha resenha) {
        Resenha resenha1 = findById(resenha.getId());
        if (resenha1.getTexto().equals(null)) {
            return true;
        }
        return false;
    }

}
