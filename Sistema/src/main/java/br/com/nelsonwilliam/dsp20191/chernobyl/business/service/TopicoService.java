package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Topico;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TopicoService {

    @Autowired
    private TopicoRepository topicoRepository;

    public List<Topico> findAll() {
        return topicoRepository.findAll();
    }

    public void deleteById(Long id) {
        topicoRepository.deleteById(id);
    }

    public Topico save(Topico topico) {
        return topicoRepository.save(topico);
    }

    public Topico findById(Long id) {
        Optional<Topico> resenha = topicoRepository.findById(id);
        return resenha.orElse(null);
    }

    public Collection<Topico> findByFilme(Long idFilme) {
        return topicoRepository.findByFilme_Id(idFilme);
    }
}
