package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Exemplo;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.ExemploRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ExemploService {

    // A anotação @Autowired faz com que o valor dessa variável seja magicamente definido pelo
    // Spring. Isso só funciona com variáveis cujo tipo seja um bean gerenciado pelo Spring
    // (em geral, classes que possuem anotações @Component, @Service, @Repository ou @Entity.)
    @Autowired
    private ExemploRepository exemploRepository;

    public List<Exemplo> findAll() {
        return exemploRepository.findAll();
    }

    public Exemplo findFirstByNome(String nome) {
        List<Exemplo> exemplos = exemploRepository.findByNome(nome);
        return exemplos.size() > 0 ? exemplos.get(0) : null;
    }
    
    public Exemplo findByid(Long id) {
        Optional<Exemplo> exemplo = exemploRepository.findById(id);
        return exemplo.get();
    }

    public void deleteById(Long id) {
        exemploRepository.deleteById(id);
    }

    public void save(Exemplo exemplo) {
        exemploRepository.save(exemplo);
    }

}
