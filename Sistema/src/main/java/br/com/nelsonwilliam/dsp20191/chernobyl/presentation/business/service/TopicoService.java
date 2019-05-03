package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.TopicoEntity;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<TopicoEntity> findAll() {
        return topicoRepository.findAll();
    }

    public void deleteById(long id) {
        topicoRepository.deleteById(id);
    }

    public void save(TopicoEntity topicoEntity) {
        topicoRepository.save(topicoEntity);
    }

}
