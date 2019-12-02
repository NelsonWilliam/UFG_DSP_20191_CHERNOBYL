package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by aluno on 03/05/19.
 */
public interface ResenhaRepository extends JpaRepository<Resenha, Long> {

    Collection<Resenha> findByFilme_Id(long idFilme);

    void deleteById(Long id);
}
