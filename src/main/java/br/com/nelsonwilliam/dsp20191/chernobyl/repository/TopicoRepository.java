package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by aluno on 03/05/19.
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    Collection<Topico> findByFilme_Id(long idFilme);

    void deleteById(Long id);
}
