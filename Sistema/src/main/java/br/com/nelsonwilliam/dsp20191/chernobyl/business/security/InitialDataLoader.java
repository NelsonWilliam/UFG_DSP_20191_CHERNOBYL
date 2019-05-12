package br.com.nelsonwilliam.dsp20191.chernobyl.business.security;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Responsável por adicionar o usuário padrão de administrador
 * caso não existam administradores no banco de dados.
 */
@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean alreadySetup = false;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;

        // Cria um usuário padrão para o administrador caso não existam administradores
        long countAdmins = usuarioRepository.countByPapeis(PapelEnum.ADMIN);
        if (countAdmins < 1) {
            Usuario admin = new Usuario();
            admin.setLogin("admin");
            admin.setSenha(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.com");
            admin.setNome("Administrador");
            admin.setPapeis(Arrays.asList(PapelEnum.ADMIN, PapelEnum.USUARIO));
            usuarioRepository.save(admin);
        }

        alreadySetup = true;
    }
}