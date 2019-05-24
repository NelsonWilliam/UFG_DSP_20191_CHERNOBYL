package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.exception.IllegalInsertException;
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
        return avaliacaoFilmeRepository.findByFilme(idFilme);
    }

    public Collection<AvaliacaoFilme> findByUsuario(Long idUsuario) {
        return avaliacaoFilmeRepository.findByUsuario(idUsuario);
    }

    public void deleteById(Long id) {
        avaliacaoFilmeRepository.deleteById(id);
    }

    public AvaliacaoFilme save(AvaliacaoFilme avaliacaoFilme) {
        return avaliacaoFilmeRepository.save(avaliacaoFilme);
    }

    public void validate(AvaliacaoFilme avalFilme, Usuario user) throws IllegalInsertException {
        // Campos nulos
        if (avalFilme.equals(null)) {
            throw new IllegalInsertException("Avaliação não pode ser nula");
        }

        if (avalFilme.getGrauRadiacao() == null) {
            throw new IllegalInsertException("O grau de radiação não pode ser nulo");
        }

        if (avalFilme.getFilme().equals(null)) {
            throw new IllegalInsertException("Avaliação deve possuir um filme");
        }

        if (avalFilme.getUsuario().equals(null)) {
            throw new IllegalInsertException("Avaliação deve ser possuir usuário");
        }

        if (user.equals(null)) {
            throw new IllegalInsertException("Avaliação deve ser feita por um usuário");
        }

        // Integridade
        if (avalFilme.getUsuario().getId() != user.getId()) {
            throw new IllegalInsertException("Um usuário não pode fazer uma avaliação por outro usuário");
        }

        // Avaliacao duplicada
        if (!findById(avalFilme.getId()).equals(null)) {
            throw new IllegalInsertException("Avaliação já existe");
        }

        // Se o usuario for o mesmo, a avaliacao deve ser diferente
        Collection<AvaliacaoFilme> avaliacoes = findByUsuario(user.getId());
        for (AvaliacaoFilme aval : avaliacoes) {
            if (aval.getUsuario().getId() == user.getId()) {
                if (aval.getGrauRadiacao() == avalFilme.getGrauRadiacao()) {
                    throw new IllegalInsertException("Avaliação por um mesmo usuário deve ser diferente da anterior");
                }
            }
        }


    }

    public boolean jaExistePorUsuario(Long idUsuario) {
        Collection<AvaliacaoFilme> avaliacoes = findByUsuario(idUsuario);
        for (AvaliacaoFilme aval : avaliacoes) {
            if (aval.getUsuario().getId() == idUsuario) {
                return true;
            }
        }
        return false;
    }

    public boolean jaExistePorFilme(Long idFilme) {
        Collection<AvaliacaoFilme> avaliacoes = findByFilme(idFilme);
        for (AvaliacaoFilme aval : avaliacoes) {
            if (aval.getUsuario().getId() == idFilme) {
                return true;
            }
        }
        return false;
    }


}
