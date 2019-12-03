package br.com.nelsonwilliam.dsp20191.chernobyl.repository;


import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoFilmeRepository extends JpaRepository<AvaliacaoFilme, Long> {

    Optional<AvaliacaoFilme> findByFilme_IdAndUsuario_Id(Long idFilme, Long idUsuario);

    void deleteById(Long id);
}
