package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UsuarioService usuarioService;

    @SuppressWarnings("unused") // Método invocado diretamente nos templates
    public boolean isAuthenticated() {
        return getUsuario() != null;
    }

    public boolean isAdmin() {
        Authentication authentication = getAuthentication();
        return authentication.isAuthenticated()
                && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public Usuario getUsuario() {
        Authentication authentication = getAuthentication();
        return usuarioService.findByLogin(authentication.getName());
    }

    public Long getIdUsuario() {
        Usuario usuario = getUsuario();
        return usuario == null ? null : usuario.getId();
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
