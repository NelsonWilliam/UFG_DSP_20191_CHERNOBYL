package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Collection<Pessoa> findByCargo(CargoEnum cargo);

    void deleteById(Long id);
}
