package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<Topico> findAll() {
        return topicoRepository.findAll();
    }

    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }

    public void save(Topico topico) {
        topicoRepository.save(topico);
    }

}
