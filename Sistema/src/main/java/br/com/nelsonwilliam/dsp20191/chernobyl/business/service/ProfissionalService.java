package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Profissional;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
