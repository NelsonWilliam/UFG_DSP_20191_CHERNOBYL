package br.com.nelsonwilliam.dsp20191.chernobyl.repository;


import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, Long> {

    Collection<AvaliacaoFilme> findByFilme_Id(Long idFilme);

    Collection<AvaliacaoFilme> findByUsuario_Id(Long idUsuario);

    Optional<AvaliacaoFilme> findByFilme_IdAndUsuario_Id(Long idFilme, Long idUsuario);

    long countByFilme_Id(Long idFilme);

    long countByUsuario_Id(Long idUsuario);

    void deleteById(Long id);
}
