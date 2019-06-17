package br.com.nelsonwilliam.dsp20191.chernobyl.business.security;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.data.repository.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.utils.Utilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private TopicoRepository topicoRepository;

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

        Pessoa jennifer = new Pessoa();
        jennifer.setNome("Jennifer Lawrence");
        jennifer.setCargo(CargoEnum.ATOR);
        jennifer.setImage(Utilitario.getPersonImgString());
        jennifer = pessoaRepository.save(jennifer);

        Pessoa aronofsky = new Pessoa();
        aronofsky.setNome("Darren Aronofsky");
        aronofsky.setCargo(CargoEnum.DIRETOR);
        aronofsky.setImage(Utilitario.getPersonImgString());
        aronofsky = pessoaRepository.save(aronofsky);

        Pessoa javier = new Pessoa();
        javier.setNome("Javier Barden");
        javier.setCargo(CargoEnum.ATOR);
        javier.setImage(Utilitario.getPersonImgString());
        javier = pessoaRepository.save(javier);

        Pessoa moss = new Pessoa();
        moss.setNome("Elisabeth Moss");
        moss.setCargo(CargoEnum.ATOR);
        moss.setImage(Utilitario.getPersonImgString());
        moss = pessoaRepository.save(moss);

        Pessoa bay = new Pessoa();
        bay.setNome("Michael Bay");
        bay.setCargo(CargoEnum.DIRETOR);
        bay.setImage(Utilitario.getPersonImgString());
        bay = pessoaRepository.save(bay);

        Pessoa port = new Pessoa();
        port.setNome("Natalie Portman");
        port.setCargo(CargoEnum.ATOR);
        port.setImage(Utilitario.getPersonImgString());
        port = pessoaRepository.save(port);

        Pessoa mike = new Pessoa();
        mike.setNome("Mike Nichols");
        mike.setCargo(CargoEnum.DIRETOR);
        mike.setImage(Utilitario.getPersonImgString());
        mike = pessoaRepository.save(mike);

        Filme filme = new Filme();
        filme.setTitulo("O Fim do Mundo");
        filme.setAtores(Arrays.asList(bette, fernanda));
        filme.setDiretor(quentin);
        filme.setPremiacoes(Arrays.asList("Pior filme de 2021"));
        filme.setImage(Utilitario.getFilmImgString());
        filme = filmeRepository.save(filme);

        Filme mother = new Filme();
        mother.setTitulo("Mother!");
        mother.setAtores(Arrays.asList(jennifer, javier));
        mother.setDiretor(aronofsky);
        mother.setPremiacoes(Arrays.asList("Pior filme de 2017", "Pior roteiro", "Pior cena grotesca"));
        mother.setImage(Utilitario.getFilmImgString());
        mother = filmeRepository.save(mother);

        Filme cient = new Filme();
        cient.setTitulo("The Cientologia's Tale");
        cient.setAtores(Arrays.asList(moss));
        cient.setDiretor(bay);
        cient.setPremiacoes(Arrays.asList("Pior filme de 2018"));
        cient.setImage(Utilitario.getFilmImgString());
        cient = filmeRepository.save(cient);

        Filme closer = new Filme();
        closer.setTitulo("Closer: Perto Demais");
        closer.setAtores(Arrays.asList(port));
        closer.setDiretor(mike);
        closer.setPremiacoes(Arrays.asList("Pior roteiro adaptado"));
        closer.setImage(Utilitario.getFilmImgString());
        closer = filmeRepository.save(closer);

        Resenha res = new Resenha();
        res.setAutor(user);
        res.setTexto("Não gostei, ou seja, é muito bom");
        res.setFilme(filme);
        res = resenhaRepository.save(res);

        Resenha resMo = new Resenha();
        resMo.setAutor(user);
        resMo.setTexto("Ícone injustiçado no framboesa de ouro. Merecia levar TODOS.");
        resMo.setFilme(mother);
        resMo = resenhaRepository.save(resMo);

        Resenha resCi = new Resenha();
        resCi.setAutor(user);
        resCi.setTexto("Não sei o que é pior, o roteiro, ou o fandom que defende.");
        resCi.setFilme(cient);
        resCi = resenhaRepository.save(resCi);

        Resenha resCl = new Resenha();
        resCl.setAutor(user);
        resCl.setTexto("Swing");
        resCl.setFilme(closer);
        resCl = resenhaRepository.save(resCl);

        Topico topico = new Topico();
        topico.setAutor(user);
        topico.setFilme(cient);
        topico.setTexto("Pior cara de sofrimento");
        topico = topicoRepository.save(topico);

        Topico topico2 = new Topico();
        topico2.setAutor(user);
        topico2.setFilme(closer);
        topico2.setTexto("Closer: CHATO DEMAIS");
        topico2 = topicoRepository.save(topico2);

        Topico topico3 = new Topico();
        topico3.setAutor(user);
        topico3.setFilme(mother);
        topico3.setTexto("Não senta na pia, ela não tá chumbada");
        topico3 = topicoRepository.save(topico3);

        alreadySetup = true;
    }
}