package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoTopico;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.AvaliacaoTopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvaliacaoTopicoService {

    @Autowired
    private AvaliacaoTopicoRepository avaliacaoTopicoRepository;

    public List<AvaliacaoTopico> findAll() {
        return avaliacaoTopicoRepository.findAll();
    }

    public Optional<AvaliacaoTopico> findById(Long id) {
        return avaliacaoTopicoRepository.findById(id);
    }

    public Collection<AvaliacaoTopico> findByTopico(Long idTopico) {
        return avaliacaoTopicoRepository.findByTopico_Id(idTopico);
    }

    public Collection<AvaliacaoTopico> findByUsuario(Long idUsuario) {
        return avaliacaoTopicoRepository.findByUsuario_Id(idUsuario);
    }

    public AvaliacaoTopico findByTopicoAndUsuario(Long idTopico, Long idUsuario) {
        return avaliacaoTopicoRepository.findByTopico_IdAndUsuario_Id(idTopico, idUsuario).orElse(null);
    }

    public void deleteById(Long id) {
        avaliacaoTopicoRepository.deleteById(id);
    }

    public AvaliacaoTopico save(AvaliacaoTopico avaliacaoTopico) {
        // Se já existe avaliação desse usuário para essa resenha, substitui a avaliação anterior em vez de criar uma nova
        AvaliacaoTopico avaliacaoExistente = findByTopicoAndUsuario(avaliacaoTopico.getTopico().getId(), avaliacaoTopico.getUsuario().getId());
        if (avaliacaoExistente != null) {
            avaliacaoTopico.setId(avaliacaoExistente.getId());
        }
        return avaliacaoTopicoRepository.save(avaliacaoTopico);
    }

    public boolean existePorUsuario(Long idUsuario) {
        return avaliacaoTopicoRepository.countByUsuario_Id(idUsuario) > 0;
    }

    public boolean existePorResenha(Long idResenha) {
        return avaliacaoTopicoRepository.countByTopico_Id(idResenha) > 0;
    }

    public double calcularPorcentagem(Long idResenha) {
        Double soma = 0.0;
        Long contagem = 0L;
        Collection<AvaliacaoTopico> avaliacoes = findByTopico(idResenha);
        if (avaliacoes.isEmpty())
            return -1.0;
        for (AvaliacaoTopico ava : avaliacoes) {
            soma += ava.isPositiva() ? 1.0 : 0.0;
            contagem++;
        }
        if (contagem == 0)
            return -1.0;
        return soma / contagem;
    }

}
