package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aluno on 03/05/19.
 */
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    void deleteById(Long id);
}
