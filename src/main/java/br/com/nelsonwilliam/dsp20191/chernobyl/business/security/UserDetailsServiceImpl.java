package br.com.nelsonwilliam.dsp20191.chernobyl.business.security;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Serviço utilizado pela segurança para obter dados credenciais de
 * um usuário a partir de seu username (login).
 */
@Service("userDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user with " +
                        "login = " + username));

        return new org.springframework.security.core.userdetails.User(
                usuario.getLogin(), usuario.getSenha(), getAuthorities(usuario.getPapeis()));
    }

    private List<GrantedAuthority> getAuthorities(Collection<PapelEnum> papeis) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (PapelEnum papel : papeis)
            authorities.add(new SimpleGrantedAuthority(papel.getAuthority()));
        return authorities;
    }
}