package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por realizar a autenticação, atribuindo ou restringindo privilégios
 * com base nos papéis.
 */
@Service
@Transactional
public class AuthService {

    /**
     * Representa o usuário a ser autenticado.
     */
    @Autowired
    private UsuarioService usuarioService;

    /**
     * Verifica se o usuário está autenticado.
     * Método invocado diretamente nos templates.
     *
     * @return O status da autenticação.
     */
    @SuppressWarnings("unused")
    public boolean isAuthenticated() {
        return getUsuario() != null;
    }

    /**
     * Verifica se o usuário é um ADMIN.
     *
     * @return O status de ADMIN do usuário.
     */
    public boolean isAdmin() {
        Authentication authentication = getAuthentication();
        return authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    /**
     * Obtém uma instância de usuário a partir de seu nome.
     *
     * @return Instância de Usuario.
     */
    public Usuario getUsuario() {
        Authentication authentication = getAuthentication();
        return usuarioService.findByLogin(authentication.getName());
    }

    /**
     * Obtém o id do usuário.
     *
     * @return O id do usuário em questão.
     */
    public Long getIdUsuario() {
        Usuario usuario = getUsuario();
        return usuario == null ? null : usuario.getId();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
