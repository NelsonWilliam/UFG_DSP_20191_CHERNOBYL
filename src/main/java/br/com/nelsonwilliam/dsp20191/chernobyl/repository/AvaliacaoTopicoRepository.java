package br.com.nelsonwilliam.dsp20191.chernobyl.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoTopico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

public interface AvaliacaoTopicoRepository extends JpaRepository<AvaliacaoTopico, Long> {

    Optional<AvaliacaoTopico> findByTopico_IdAndUsuario_Id(Long idTopico, Long idUsuario);

    void deleteById(Long id);

}
