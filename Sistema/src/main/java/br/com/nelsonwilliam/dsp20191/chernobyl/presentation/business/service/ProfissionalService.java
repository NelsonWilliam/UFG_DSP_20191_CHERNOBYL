package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Profissional;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class ProfissionalService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<Profissional> findAll() {
        return profissionalRepository.findAll();
    }

    public void deleteById(Long id) {
        profissionalRepository.deleteById(id);
    }

    public void save(Profissional profissional) {
        profissionalRepository.save(profissional);
    }
}
