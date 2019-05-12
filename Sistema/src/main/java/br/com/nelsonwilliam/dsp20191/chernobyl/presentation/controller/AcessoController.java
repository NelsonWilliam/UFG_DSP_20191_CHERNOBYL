package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Exemplo;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Arrays;

@Controller
public class AcessoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/testeAcesso")
    public String homeGuest(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(x -> ((GrantedAuthority) x)
                            .getAuthority()
                            .equals(PapelEnum.ADMIN.getAuthority()))) {
                return "redirect:/testeAcesso/admin";
            } else if (authentication.getAuthorities().stream()
                    .anyMatch(x -> ((GrantedAuthority) x)
                            .getAuthority()
                            .equals(PapelEnum.USUARIO.getAuthority()))) {
                return "redirect:/testeAcesso/usuario";
            }
        }
        return "redirect:/testeAcesso/convidado";
    }

    @GetMapping("/testeAcesso/admin")
    public String homeAdmin(Model model, Authentication authentication) {
        String name = authentication.getName();
        model.addAttribute("username", name);
        return "TesteAcesso/homeAdmin";
    }

    @GetMapping("/testeAcesso/usuario")
    public String homeUser(Model model, Authentication authentication) {
        String name = authentication.getName();
        model.addAttribute("username", name);
        return "TesteAcesso/homeUser";
    }

    @GetMapping("/testeAcesso/convidado")
    public String homeConvidado(Model model) {
        return "TesteAcesso/homeGuest";
    }

    @GetMapping("/testeAcesso/login")
    public String login(Model model) {
        return "TesteAcesso/login";
    }

    @GetMapping("/testeAcesso/logout")
    public String logout(Model model) {
        return "TesteAcesso/logout";
    }

    @GetMapping("/testeAcesso/cadastrar")
    public String cadastrar(Model model) {
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "TesteAcesso/cadastrar";
    }

    @PostMapping("/testeAcesso/cadastrar/salvar")
    public String cadastrarSalvar(Usuario usuario) {
        usuario.setPapeis(Arrays.asList(PapelEnum.USUARIO));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        usuarioService.save(usuario);
        return "redirect:/testeAcesso";
    }
}
