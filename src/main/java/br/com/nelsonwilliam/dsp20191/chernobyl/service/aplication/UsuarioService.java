package br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.UsuarioDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.orElse(null);
    }

    public Usuario findByEmail(String email) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElse(null);
    }

    private Usuario findByLogin(String login) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        return usuario.orElse(null);
    }

    public Long findIdByLogin(String login) {
        if (findByLogin(login) == null) {
            return null;
        } else {
            Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
            return usuario.orElse(null).getId();
        }
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario save(UsuarioDto usuarioDto, PasswordEncoder passwordEncoder) {
        Usuario usuario = usuarioDto.toUsuario(passwordEncoder);
        return usuarioRepository.save(usuario);
    }

    public Usuario autorizarUsuario(Principal principal){
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        } else {
            return usuario;
        }
    }
}
