package br.com.nelsonwilliam.dsp20191.chernobyl.repository;


import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoResenha;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoResenhaRepository extends JpaRepository<AvaliacaoResenha, Long> {

    Collection<AvaliacaoResenha> findByResenha_Id(Long idResenha);

    Collection<AvaliacaoResenha> findByUsuario_Id(Long idUsuario);

    Optional<AvaliacaoResenha> findByResenha_IdAndUsuario_Id(Long idResenha, Long idUsuario);

    long countByResenha_Id(Long idResenha);

    long countByUsuario_Id(Long idUsuario);

    void deleteById(Long id);
}
