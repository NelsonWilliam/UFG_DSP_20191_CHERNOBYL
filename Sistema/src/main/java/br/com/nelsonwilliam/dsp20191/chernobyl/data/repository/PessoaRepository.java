package br.com.nelsonwilliam.dsp20191.chernobyl.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

/**
 * Created by aluno on 03/05/19.
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Collection<Pessoa> findByCargo(CargoEnum cargo);

    void deleteById(Long id);
}
