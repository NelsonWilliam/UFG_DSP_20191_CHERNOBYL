package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by aluno on 03/05/19.
 */
public interface FilmeRepository extends JpaRepository<Filme, Long> {

    Collection<Filme> findByDiretor_Id(Long idDiretor);

    Collection<Filme> findByAtores_Id(Long idAtor);

    void deleteById(Long id);
}
