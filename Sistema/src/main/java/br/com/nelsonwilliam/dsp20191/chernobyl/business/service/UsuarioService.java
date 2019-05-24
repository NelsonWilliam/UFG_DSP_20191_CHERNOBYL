package br.com.nelsonwilliam.dsp20191.chernobyl.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

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

    public Usuario findByLogin(String login) {
        Optional<Usuario> usuario = usuarioRepository.findByLogin(login);
        return usuario.orElse(null);
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
