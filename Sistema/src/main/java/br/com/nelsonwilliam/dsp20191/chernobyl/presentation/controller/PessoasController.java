package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Pessoa;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public String pessoas(Model model) {
        // TODO: Adicionar algum tipo de paginação, já que isso seria inviável se tivesse muitos filmes.
        model.addAttribute("pessoas", pessoaService.findAllDto());
        return "pessoas/pessoas";
    }

    @GetMapping("/pessoas/{id}")
    public String pessoa(@PathVariable Long id, Model model) {
        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null)
            throw new IllegalArgumentException("Pessoa não encontrada");
        model.addAttribute("pessoaDto", PessoaDto.fromPessoa(pessoa));
        return "pessoas/pessoa";
    }

    @GetMapping("/admin/pessoas/inserir")
    public String inserir(Model model) {
        model.addAttribute("pessoaDto", new PessoaDto());
        return "pessoas/inserir";
    }

    @PostMapping("/admin/pessoas/inserir/enviar")
    public String inserirEnviar(@Valid PessoaDto pessoaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pessoas/inserir";
        }
        if (CargoEnum.fromNome(pessoaDto.getCargo()) == null) {
            bindingResult.rejectValue("cargo", "error.pessoaDto", "Cargo inválido.");
            return "pessoas/inserir";
        }

        Pessoa pessoa = pessoaDto.toPessoa();
        pessoaService.save(pessoa);
        return "redirect:/pessoas?created";
    }
}
