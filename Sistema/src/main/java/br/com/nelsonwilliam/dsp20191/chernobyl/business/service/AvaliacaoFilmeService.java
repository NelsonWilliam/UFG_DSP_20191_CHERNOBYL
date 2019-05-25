package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.AvaliacaoFilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvaliacaoFilmeService {

    @Autowired
    private AvaliacaoFilmeRepository avaliacaoFilmeRepository;

    public List<AvaliacaoFilme> findAll() {
        return avaliacaoFilmeRepository.findAll();
    }

    public Optional<AvaliacaoFilme> findById(Long id) {
        return avaliacaoFilmeRepository.findById(id);
    }

    public Collection<AvaliacaoFilme> findByFilme(Long idFilme) {
        return avaliacaoFilmeRepository.findByFilme_Id(idFilme);
    }

    public Collection<AvaliacaoFilme> findByUsuario(Long idUsuario) {
        return avaliacaoFilmeRepository.findByUsuario_Id(idUsuario);
    }

    public AvaliacaoFilme findByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        return avaliacaoFilmeRepository.findByFilme_IdAndUsuario_Id(idFilme, idUsuario).orElse(null);
    }

    public void deleteById(Long id) {
        avaliacaoFilmeRepository.deleteById(id);
    }

    public AvaliacaoFilme save(AvaliacaoFilme avaliacaoFilme) {
        // Se já existe avaliação desse usuário para esse filme, substitui a avaliação anterior em vez de criar uma nova
        AvaliacaoFilme avaliacaoExistente = findByFilmeAndUsuario(avaliacaoFilme.getFilme().getId(), avaliacaoFilme.getUsuario().getId());
        if (avaliacaoExistente != null) {
            avaliacaoFilme.setId(avaliacaoExistente.getId());
        }
        return avaliacaoFilmeRepository.save(avaliacaoFilme);
    }

    public boolean existePorUsuario(Long idUsuario) {
        return avaliacaoFilmeRepository.countByUsuario_Id(idUsuario) > 0;
    }

    public boolean existePorFilme(Long idFilme) {
        return avaliacaoFilmeRepository.countByFilme_Id(idFilme) > 0;
    }

    public double calcularGrauPorFilme(Long idFilme) {
        Double soma = 0.0;
        Long contagem = 0L;
        Collection<AvaliacaoFilme> avaliacoes = findByFilme(idFilme);
        if (avaliacoes.isEmpty())
            return 0.0;
        for (AvaliacaoFilme ava : avaliacoes) {
            if (ava.getGrauRadiacao() > 0) {
                soma += ava.getGrauRadiacao();
                contagem++;
            }
        }
        if (contagem == 0)
            return 0.0;
        return soma / contagem;
    }

}
