package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * Created by aluno on 03/05/19.
 */
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario findById(Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        return usuario.get();
    }

    public Usuario findByEmail(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public Usuario findByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        return usuario;
    }

    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public void save(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

}
