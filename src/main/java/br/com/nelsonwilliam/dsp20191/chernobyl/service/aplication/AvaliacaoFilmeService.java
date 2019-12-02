package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.AvaliacaoFilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.AvaliacaoFilmeRepository;
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

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private UsuarioService usuarioService;

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

    public AvaliacaoFilmeDto findByFilmeAndUsuario(Long idFilme, Long idUsuario) {
        AvaliacaoFilme avaliacaoFilme = findByFilmeAndUsuarioPrivate(idFilme, idUsuario);
        if(findByFilmeAndUsuarioPrivate(idFilme, idUsuario) == null) {
            return null;
        } else {
            return AvaliacaoFilmeDto.fromAvaliacaoFilme(avaliacaoFilme);
        }
    }

    private AvaliacaoFilme findByFilmeAndUsuarioPrivate(Long idFilme, Long idUsuario) {
        return avaliacaoFilmeRepository.findByFilme_IdAndUsuario_Id(idFilme, idUsuario).orElse(null);
    }

    public void deleteById(Long id) {
        avaliacaoFilmeRepository.deleteById(id);
    }

    public AvaliacaoFilme save(Long idUsuario, Long idFilme, int nota) {
        // Se já existe avaliação desse usuário para esse filme, substitui a avaliação anterior em vez de criar uma nova
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setUsuario(usuarioService.findById(idUsuario));
        aval.setFilme(filmeService.findById(idFilme));
        aval.setGrauRadiacao(nota);

        AvaliacaoFilme avaliacaoExistente = findByFilmeAndUsuarioPrivate(idFilme, idUsuario);
        if (avaliacaoExistente != null) {
            aval.setId(avaliacaoExistente.getId());
        }
        return avaliacaoFilmeRepository.save(aval);
    }

    private AvaliacaoFilme savePrivate(AvaliacaoFilme avaliacaoFilme) {
        // Se já existe avaliação desse usuário para esse filme, substitui a avaliação anterior em vez de criar uma nova
        AvaliacaoFilme avaliacaoExistente = findByFilmeAndUsuarioPrivate(avaliacaoFilme.getFilme().getId(), avaliacaoFilme.getUsuario().getId());
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
