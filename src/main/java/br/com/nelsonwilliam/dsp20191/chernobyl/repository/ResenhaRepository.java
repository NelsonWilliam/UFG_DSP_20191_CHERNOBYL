package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Resenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ResenhaRepository extends JpaRepository<Resenha, Long> {

    void deleteById(Long id);
}
