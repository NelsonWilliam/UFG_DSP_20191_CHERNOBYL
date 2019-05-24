package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.FilmeService;
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
public class FilmesController {

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/filmes")
    public String filmes(Model model) {
        // TODO: Adicionar algum tipo de paginação, já que isso seria inviável se tivesse muitos filmes.
        model.addAttribute("filmes", filmeService.findAllDto());
        return "filmes/filmes";
    }

    @GetMapping("/filmes/{id}")
    public String filme(@PathVariable Long id, Model model) {
        Filme filme = filmeService.findById(id);
        if (filme == null)
            throw new IllegalArgumentException("Filme não encontrado");
        model.addAttribute("filmeDto", FilmeDto.fromFilme(filme));
        return "filmes/filme";
    }

    @GetMapping("/admin/filmes/inserir")
    public String inserir(Model model) {
        model.addAttribute("filmeDto", new FilmeDto());
        return "filmes/inserir";
    }

    @PostMapping("/admin/filmes/inserir/enviar")
    public String inserirEnviar(@Valid FilmeDto filmeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "filmes/inserir";
        }

        Filme filme = filmeDto.toFilme(pessoaService);
        filmeService.save(filme);
        return "redirect:/filmes?created";
    }
}
