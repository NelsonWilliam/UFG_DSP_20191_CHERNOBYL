package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.TopicoDto;
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
public class TopicosController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private TopicoService topicoService;

    @GetMapping(path = "/filmes/{idFilme}", params = {"avaliarTopico", "positivo"})
    public String avaliarTopico(@PathVariable Long idFilme,
                                @RequestParam("avaliarTopico") long idTopico,
                                @RequestParam("positivo") boolean positivo,
                                Model model,
                                HttpServletRequest request) {
        topicoService.avaliar(idTopico, positivo);

        FilmeDto filmeDto = filmeService.findById(idFilme);
        model.addAttribute("filmeDto", filmeDto);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: topicos";
        } else {
            return "filmes/filme";
        }
    }

    @GetMapping("/filmes/{idFilme}/editar-topico")
    public String editarTopicoNovo(@PathVariable Long idFilme, Model model) {
        TopicoDto topicoDto = new TopicoDto();
        topicoDto.setIdFilme(idFilme);
        model.addAttribute("topicoDto", topicoDto);
        return "filmes/editar-topico";
    }

    @GetMapping("/filmes/{idFilme}/editar-topico/{idTopico}")
    public String editarTopicoExistente(@PathVariable Long idFilme, @PathVariable Long idTopico, Model model) {
        TopicoDto topicoDto = topicoService.findById(idTopico);
        model.addAttribute("topicoDto", topicoDto);
        return "filmes/editar-topico";
    }

    @PostMapping("/filmes/{idFilme}/editar-topico/enviar")
    public String editarTopicoEnviar(@PathVariable Long idFilme, @Valid TopicoDto topicoDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "filmes/editar-topico";
        }

        Long idAnterior = topicoDto.getId();
        Long idNovo = topicoService.salvarSeForDono(topicoDto).getId();
        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + idFilme + "?updatedtopico=" + idAnterior;
        } else {
            return "redirect:/filmes/" + idFilme + "?createdtopico=" + idNovo;
        }
    }

    @GetMapping("/filmes/{idFilm}/excluir-topico/{idTopico}")
    public String excluirTopico(@PathVariable Long idFilm, @PathVariable Long idTopico) {
        topicoService.deletarSeForDono(idTopico);
        return "redirect:/filmes/" + idFilm + "?deletedtopico=" + idTopico;
    }
}
