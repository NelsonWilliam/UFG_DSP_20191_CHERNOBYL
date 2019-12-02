package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoResenhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AvaliacaoResenhaService {

    @Autowired
    private AvaliacaoResenhaRepository avaliacaoResenhaRepository;

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private UsuarioService usuarioService;

    public List<AvaliacaoResenha> findAll() {
        return avaliacaoResenhaRepository.findAll();
    }

    public Optional<AvaliacaoResenha> findById(Long id) {
        return avaliacaoResenhaRepository.findById(id);
    }

    public Collection<AvaliacaoResenha> findByResenha(Long idResenha) {
        return avaliacaoResenhaRepository.findByResenha_Id(idResenha);
    }

    public Collection<AvaliacaoResenha> findByUsuario(Long idUsuario) {
        return avaliacaoResenhaRepository.findByUsuario_Id(idUsuario);
    }

    public AvaliacaoResenha findByResenhaAndUsuario(Long idResenha, Long idUsuario) {
        return avaliacaoResenhaRepository.findByResenha_IdAndUsuario_Id(idResenha, idUsuario).orElse(null);
    }

    public void deleteById(Long id) {
        avaliacaoResenhaRepository.deleteById(id);
    }

    public Long save(Long idUsuario, Long idResenha, boolean positivo) {
        AvaliacaoResenha aval = new AvaliacaoResenha();
        aval.setUsuario(usuarioService.findById(idUsuario));
        aval.setResenha(resenhaService.findById(idResenha));
        aval.setPositiva(positivo);
        // Se já existe avaliação desse usuário para essa resenha, substitui a avaliação anterior em vez de criar uma nova
        AvaliacaoResenha avaliacaoExistente = findByResenhaAndUsuario(idResenha, idUsuario);
        if (avaliacaoExistente != null) {
            aval.setId(avaliacaoExistente.getId());
        }
        return avaliacaoResenhaRepository.save(aval).getId();
    }

    public boolean existePorUsuario(Long idUsuario) {
        return avaliacaoResenhaRepository.countByUsuario_Id(idUsuario) > 0;
    }

    public boolean existePorResenha(Long idResenha) {
        return avaliacaoResenhaRepository.countByResenha_Id(idResenha) > 0;
    }

    public double calcularPorcentagem(Long idResenha) {
        Double soma = 0.0;
        Long contagem = 0L;
        Collection<AvaliacaoResenha> avaliacoes = findByResenha(idResenha);
        if (avaliacoes.isEmpty())
            return -1.0;
        for (AvaliacaoResenha ava : avaliacoes) {
            soma += ava.isPositiva() ? 1.0 : 0.0;
            contagem++;
        }
        if (contagem == 0)
            return -1.0;
        return soma / contagem;
    }

}
