package br.com.nelsonwilliam.dsp20191.chernobyl.service.application;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.UsuarioDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.repository.UsuarioRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.assemblers.UsuarioAssembler;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions.InvalidFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Serviço responsável por manter a entidade Usuário.
 */
@Service
@Transactional
public class UsuarioService extends CrudService<Usuario, UsuarioDto, Long, UsuarioRepository> {

    /**
     * Responsável pela conversão entre a entidade "Usuário", seu respectivo DTO e vice-versa.
     */
    @Autowired
    private UsuarioAssembler usuarioAssembler;

    /**
     * Restringe novos cadastros contendo logins e/ou emails previamente utilizados.
     *
     * @param usuarioDto Instância de UsuarioDto, com as informações a serem validadas.
     * @throws InvalidFieldException Exceção de campos 'login' ou 'email' inválidos.
     */
    public void validar(UsuarioDto usuarioDto) throws InvalidFieldException {
        if (findByLogin(usuarioDto.getLogin()) != null)
            throw new InvalidFieldException("login", "login já utilizado");

        if (findByEmail(usuarioDto.getEmail()) != null)
            throw new InvalidFieldException("email", "e-mail já utilizado");
    }

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param usuarioDto Instância de UsuarioDto com as informações a serem adicionadas.
     */
    public void salvar(UsuarioDto usuarioDto) {
        Usuario usuario = convertDtoToEntity(usuarioDto);
        getRepository().save(usuario);
    }

    protected Usuario findByEmail(String email) {
        return getRepository().findByEmail(email).orElse(null);
    }

    protected Usuario findByLogin(String login) {
        return getRepository().findByLogin(login).orElse(null);
    }

    @Override
    protected UsuarioDto convertEntityToDto(Usuario usuario) {
        return usuario == null ? null : usuarioAssembler.toDto(usuario);
    }

    @Override
    protected Usuario convertDtoToEntity(UsuarioDto usuarioDto) {
        return usuarioDto == null ? null : usuarioAssembler.toEntity(usuarioDto);
    }
}
