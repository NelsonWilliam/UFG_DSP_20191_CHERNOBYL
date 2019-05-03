package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.FilmeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by aluno on 03/05/19.
 */
public interface FilmeRepository extends JpaRepository<FilmeEntity, Long> {

    List<FilmeEntity> findByGrauRadioatividade(float grauRadioatividade);

    List<FilmeEntity> findByAtor(long idAtor);

    List<FilmeEntity> findByAtriz(long idAtriz);

    void deleteById(Long id);
}
