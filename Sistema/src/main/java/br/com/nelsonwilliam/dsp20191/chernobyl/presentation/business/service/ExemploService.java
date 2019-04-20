package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.ExemploEntity;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.ExemploRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExemploService {

    // A anotação @Autowired faz com que o valor dessa variável seja magicamente definido pelo
    // Spring. Isso só funciona com variáveis cujo tipo seja um bean gerenciado pelo Spring
    // (em geral, classes que possuem anotações @Component, @Service, @Repository ou @Entity.)
    @Autowired
    private ExemploRepository exemploRepository;

    public List<ExemploEntity> findAll() {
        return exemploRepository.findAll();
    }

    public ExemploEntity findFirstByNome(String nome) {
        List<ExemploEntity> exemplos = exemploRepository.findByNome(nome);
        return exemplos.size() > 0 ? exemplos.get(0) : null;
    }
}
