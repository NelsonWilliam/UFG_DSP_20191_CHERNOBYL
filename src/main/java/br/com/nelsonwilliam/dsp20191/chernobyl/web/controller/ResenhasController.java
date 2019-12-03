package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.ResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@Transactional
@Controller
public class ResenhasController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private ResenhaService resenhaService;

    @GetMapping(path = "/filmes/{idFilme}", params = {"avaliarResenha", "positivo"})
    public String avaliarResenha(@PathVariable Long idFilme,
                                 @RequestParam("avaliarResenha") long idResenha,
                                 @RequestParam("positivo") boolean positivo,
                                 Model model,
                                 HttpServletRequest request) {
        resenhaService.avaliar(idResenha, positivo);

        FilmeDto filmeDto = filmeService.findById(idFilme);
        model.addAttribute("filmeDto", filmeDto);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: resenhas";
        } else {
            return "filmes/filme";
        }
    }

    @GetMapping("/filmes/{idFilme}/editar-resenha")
    public String editarResenhaNova(@PathVariable Long idFilme, Model model) {
        ResenhaDto resenhaDto = new ResenhaDto();
        resenhaDto.setIdFilme(idFilme);
        model.addAttribute("resenhaDto", resenhaDto);
        return "filmes/editar-resenha";
    }

    @GetMapping("/filmes/{idFilme}/editar-resenha/{idResenha}")
    public String editarResenhaExistente(@PathVariable Long idFilme, @PathVariable Long idResenha, Model model) {
        ResenhaDto resenhaDto = resenhaService.findById(idResenha);
        model.addAttribute("resenhaDto", resenhaDto);
        return "filmes/editar-resenha";
    }

    @PostMapping("/filmes/{idFilme}/editar-resenha/enviar")
    public String editarResenhaEnviar(@PathVariable Long idFilme, @Valid ResenhaDto resenhaDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "filmes/editar-resenha";
        }

        Long idAnterior = resenhaDto.getId();
        Long idNovo = resenhaService.salvarSeForDono(resenhaDto).getId();
        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + idFilme + "?updatedresenha=" + idAnterior;
        } else {
            return "redirect:/filmes/" + idFilme + "?createdresenha=" + idNovo;
        }
    }

    @GetMapping("/filmes/{idFilme}/excluir-resenha/{idResenha}")
    public String excluirResenha(@PathVariable Long idFilme, @PathVariable Long idResenha) {
        resenhaService.deletarSeForDono(idResenha);
        return "redirect:/filmes/" + idFilme + "?deletedresenha=" + idResenha;
    }
}
