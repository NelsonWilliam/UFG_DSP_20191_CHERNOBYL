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
import java.util.Objects;

@Controller
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public String verTodos(Model model) {
        // TODO: Adicionar algum tipo de paginação, já que isso seria inviável se tivesse muitos filmes.
        model.addAttribute("pessoas", pessoaService.findAllDto());
        return "pessoas/pessoas";
    }

    @GetMapping("/pessoas/{id}")
    public String verUm(@PathVariable Long id, Model model) {
        Pessoa pessoa = pessoaService.findById(id);
        if (pessoa == null)
            throw new IllegalArgumentException("Pessoa não encontrada");
        model.addAttribute("pessoaDto", PessoaDto.fromPessoa(pessoa));
        return "pessoas/pessoa";
    }

    @GetMapping("/admin/pessoas/editar")
    public String editarNovo(Model model) {
        model.addAttribute("pessoaDto", new PessoaDto());
        return "pessoas/editar";
    }

    @GetMapping("/admin/pessoas/editar/{id}")
    public String editarExistente(@PathVariable Long id, Model model) {
        model.addAttribute("pessoaDto", PessoaDto.fromPessoa(pessoaService.findById(id)));
        return "pessoas/editar";
    }

    @PostMapping("/admin/pessoas/editar/enviar")
    public String editarEnviar(@Valid PessoaDto pessoaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pessoas/editar";
        }
        if (CargoEnum.fromNome(pessoaDto.getCargo()) == null) {
            bindingResult.rejectValue("cargo", "error.pessoaDto", "Cargo inválido.");
            return "pessoas/editar";
        }

        Pessoa pessoa = pessoaDto.toPessoa();
        Long idAnterior = pessoa.getId();
        pessoa = pessoaService.save(pessoa);
        Long idNovo = pessoa.getId();

        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/pessoas/" + idNovo + "?updated";
        } else {
            return "redirect:/pessoas?created";
        }
    }

    @GetMapping("/admin/pessoas/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        pessoaService.deleteById(id);
        return "redirect:/pessoas?deleted";
    }
}