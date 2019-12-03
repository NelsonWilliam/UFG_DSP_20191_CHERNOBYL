package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Topico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by aluno on 03/05/19.
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    void deleteById(Long id);
}
