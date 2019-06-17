package br.com.nelsonwilliam.dsp20191.chernobyl.business.security;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.FilmeRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.PessoaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.ResenhaRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.UsuarioRepository;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.utils.Utilitario;
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
    private PessoaRepository pessoaRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private ResenhaRepository resenhaRepository;

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

        // TODO Remover
        Usuario user = new Usuario();
        user.setLogin("user");
        user.setSenha(passwordEncoder.encode("user"));
        user.setEmail("user@user.com");
        user.setNome("Usuário");
        user.setPapeis(Arrays.asList(PapelEnum.USUARIO));
        user = usuarioRepository.save(user);
        Pessoa bette = new Pessoa();
        bette.setNome("Bette Davis");
        bette.setCargo(CargoEnum.ATOR);
        StringBuilder sb = new StringBuilder();
        bette.setImage(Utilitario.getPersonImgString());
        bette = pessoaRepository.save(bette);

        Pessoa fernanda = new Pessoa();
        fernanda.setNome("Fernanda Montenegro");
        fernanda.setCargo(CargoEnum.ATOR);
        fernanda.setImage(Utilitario.getPersonImgString());
        fernanda = pessoaRepository.save(fernanda);

        Pessoa quentin = new Pessoa();
        quentin.setNome("Qutentin Tarantulino");
        quentin.setCargo(CargoEnum.DIRETOR);
        quentin.setImage(Utilitario.getPersonImgString());
        quentin = pessoaRepository.save(quentin);

        Filme filme = new Filme();
        filme.setTitulo("O Fim do Mundo");
        filme.setAtores(Arrays.asList(bette, fernanda));
        filme.setDiretor(quentin);
        filme.setPremiacoes(Arrays.asList("Pior filme de 2021"));
        filme.setImage(Utilitario.getFilmImgString());
        filme = filmeRepository.save(filme);

        Resenha res = new Resenha();
        res.setAutor(user);
        res.setTexto("Não gostei, ou seja, é muito bom");
        res.setFilme(filme);
        res = resenhaRepository.save(res);

        alreadySetup = true;
    }
}