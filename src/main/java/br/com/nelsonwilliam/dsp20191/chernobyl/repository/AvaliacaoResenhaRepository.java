package br.com.nelsonwilliam.dsp20191.chernobyl.repository;


import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoResenhaRepository extends JpaRepository<AvaliacaoResenha, Long> {

    Optional<AvaliacaoResenha> findByResenha_IdAndUsuario_Id(Long idResenha, Long idUsuario);

    void deleteById(Long id);
}
