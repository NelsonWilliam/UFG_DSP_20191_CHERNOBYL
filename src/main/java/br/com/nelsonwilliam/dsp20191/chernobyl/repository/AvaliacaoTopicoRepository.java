package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoTopico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoTopicoRepository extends JpaRepository<AvaliacaoTopico, Long> {

    Collection<AvaliacaoTopico> findByTopico_Id(Long idTopico);

    Collection<AvaliacaoTopico> findByUsuario_Id(Long idUsuario);

    Optional<AvaliacaoTopico> findByTopico_IdAndUsuario_Id(Long idTopico, Long idUsuario);

    long countByTopico_Id(Long idTopico);

    long countByUsuario_Id(Long idUsuario);

    void deleteById(Long id);

}
