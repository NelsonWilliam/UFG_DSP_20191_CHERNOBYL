package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.UsuarioEntity;
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

    public List<UsuarioEntity> findAll() {
        return usuarioRepository.findAll();
    }

    public UsuarioEntity findByid(long id) {
        Optional<UsuarioEntity> usuario = usuarioRepository.findById(id);
        return usuario.get();
    }

    public UsuarioEntity findByEmail(String email) {
        UsuarioEntity usuario = usuarioRepository.findByEmail(email);
        return usuario;
    }

    public UsuarioEntity findByLogin(String login) {
        UsuarioEntity usuario = usuarioRepository.findByLogin(login);
        return usuario;
    }


    public void deleteById(long id) {
        usuarioRepository.deleteById(id);
    }

    public void save(UsuarioEntity usuarioEntity) {
        usuarioRepository.save(usuarioEntity);
    }

}
