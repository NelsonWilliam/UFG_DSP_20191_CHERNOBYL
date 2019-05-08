package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Collection<Filme> findByDiretor (Long idDiretor);

    Collection<Filme> findByAtores (Long idDiretor);

    void deleteById(Long id);
}
