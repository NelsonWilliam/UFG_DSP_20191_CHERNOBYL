package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
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

    public boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication.isAuthenticated();
    }

    public boolean isAdmin() {
        Authentication authentication = getAuthentication();
        return authentication.isAuthenticated() && authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    public Usuario getUsuario() {
        Authentication authentication = getAuthentication();
        return usuarioService.findByLogin(authentication.getName());
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

}
