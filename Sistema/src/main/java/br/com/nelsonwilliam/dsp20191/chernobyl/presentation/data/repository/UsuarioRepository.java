package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by aluno on 03/05/19.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Usuario findByLogin(String login);

    Usuario findByEmail(String email);

    void deleteById(Long id);
}
