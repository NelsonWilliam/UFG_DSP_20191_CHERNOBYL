package br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.UsuarioDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Responsável pela conversão entre a Entidade "Usuário" e um Data Transfer Object e vice-versa.
 */
@Component
public class UsuarioAssembler {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Converte um Data Transfer Object "Usuario" em uma Entidade "Usuario".
     *
     * @param usuarioDto Instância de UsuarioDto.
     * @return Instância de Usuario.
     */
    public Usuario toEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDto.getId());
        usuario.setLogin(usuarioDto.getLogin());
        usuario.setNome(usuarioDto.getNome());
        usuario.setSenha(passwordEncoder.encode(usuarioDto.getSenha()));
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPapeis(Collections.singletonList(PapelEnum.USUARIO));
        return usuario;
    }

    /**
     * Converte uma Entidade "Usuario" em um Data Transfer Object "Usuario".
     *
     * @param usuario Instância de Usuario.
     * @return usuarioDto Instância de UsuarioDto.
     */
    public UsuarioDto toDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getId());
        dto.setLogin(usuario.getLogin());
        dto.setNome(usuario.getNome());
        dto.setSenha(usuario.getSenha());
        dto.setEmail(usuario.getEmail());
        return dto;
    }

}
