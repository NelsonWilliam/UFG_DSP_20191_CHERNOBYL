package br.com.nelsonwilliam.dsp20191.chernobyl.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aluno on 03/05/19.
 */
public interface ResenhaRepository extends JpaRepository<Resenha, Long> {

    void deleteById(Long id);
}
