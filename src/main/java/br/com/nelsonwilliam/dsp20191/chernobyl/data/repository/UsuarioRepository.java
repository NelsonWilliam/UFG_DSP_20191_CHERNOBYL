package br.com.nelsonwilliam.dsp20191.chernobyl.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;

/**
 * Created by aluno on 03/05/19.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByLogin(String login);

    Optional<Usuario> findByEmail(String email);

    Collection<Usuario> findByPapeis(PapelEnum papel);

    long countByPapeis(PapelEnum papel);

    void deleteById(Long id);
}