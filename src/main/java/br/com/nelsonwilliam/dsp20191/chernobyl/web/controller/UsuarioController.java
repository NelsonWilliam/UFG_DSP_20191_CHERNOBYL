package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.UsuarioDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions.InvalidFieldException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Transactional
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/usuario/login")
    public String login() {
        return "usuario/login";
    }

    @GetMapping("/usuario/cadastrar")
    public String cadastrar(Model model) {
        UsuarioDto usuarioDto = new UsuarioDto();
        model.addAttribute("usuarioDto", usuarioDto);
        return "usuario/cadastrar";
    }

    @PostMapping("/usuario/cadastrar/enviar")
    public String cadastrarEnviar(@Valid UsuarioDto usuarioDto, BindingResult bindingResult) {
        try {
            usuarioService.validar(usuarioDto);
        } catch (InvalidFieldException ife) {
            bindingResult.rejectValue(ife.getField(), "error.usuarioDto", ife.getReason());
        }

        if (bindingResult.hasErrors())
            return "usuario/cadastrar";

        usuarioService.salvar(usuarioDto);
        return "redirect:/usuario/login?created";
    }

}
