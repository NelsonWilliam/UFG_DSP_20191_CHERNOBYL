package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.FilmeDto;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Transactional
@Controller
public class FilmesController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private FilmeService filmeService;

    @GetMapping("/filmes")
    public String getAll(Model model) {
        List<FilmeDto> filmes = filmeService.findAll();
        model.addAttribute("filmes", filmes);
        return "filmes/filmes";
    }

    @GetMapping("/filmes/{id}")
    public String getById(@PathVariable Long id, Model model) {
        FilmeDto filmeDto = filmeService.findById(id);
        model.addAttribute("filmeDto", filmeDto);
        return "filmes/filme";
    }

    @GetMapping("/admin/filmes/editar")
    public String editarNovo(Model model) {
        FilmeDto filmeDto = new FilmeDto();
        model.addAttribute("filmeDto", filmeDto);
        return "filmes/editar";
    }

    @GetMapping("/admin/filmes/editar/{id}")
    public String editarExistente(@PathVariable Long id, Model model) {
        FilmeDto filmeDto = filmeService.findById(id);
        model.addAttribute("filmeDto", filmeDto);
        return "filmes/editar";
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "addAtor")
    public String editarAddAtor(FilmeDto filmeDto,
                                @RequestParam("addAtor") long idAtor,
                                HttpServletRequest request) {
        if (!filmeDto.getIdsAtores().contains(idAtor))
            filmeDto.getIdsAtores().add(idAtor);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: atores";
        } else {
            return "filmes/editar";
        }
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "removeAtor")
    public String editarRemoveAtor(FilmeDto filmeDto,
                                   @RequestParam("removeAtor") int index,
                                   HttpServletRequest request) {
        filmeDto.getIdsAtores().remove(index);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: atores";
        } else {
            return "filmes/editar";
        }
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "addPremiacao")
    public String editarAddPremiacao(FilmeDto filmeDto,
                                     @RequestParam("addPremiacao") String premiacao,
                                     HttpServletRequest request) {
        if (premiacao != null && premiacao.length() > 0 && !filmeDto.getPremiacoes().contains(premiacao))
            filmeDto.getPremiacoes().add(premiacao);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: premiacoes";
        } else {
            return "filmes/editar";
        }
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "removePremiacao")
    public String editarRemovePremiacao(FilmeDto filmeDto,
                                        @RequestParam("removePremiacao") int index,
                                        HttpServletRequest request) {
        filmeDto.getPremiacoes().remove(index);

        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: premiacoes";
        } else {
            return "filmes/editar";
        }
    }

    @PostMapping("/admin/filmes/editar/enviar")
    public String editarEnviar(@Valid FilmeDto filmeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "filmes/editar";
        }

        Long idAnterior = filmeDto.getId();
        Long idNovo = filmeService.salvar(filmeDto).getId();
        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + idNovo + "?updated";
        } else {
            return "redirect:/filmes?created";
        }
    }

    @GetMapping("/admin/filmes/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        filmeService.deleteById(id);
        return "redirect:/filmes?deleted";
    }

    @PostMapping("/admin/filmes/{id}/alterar_imagem")
    public String alterarImagem(@PathVariable Long id,
                                @RequestParam("file") MultipartFile file,
                                RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("err", "Selecione um arquivo!");
            return "redirect:/admin/filmes/editar/{id}";
        }

        try {
            filmeService.salvarImagem(file, id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Não foi possível realizar upload: " + e.getMessage());
            return "redirect:/admin/filmes/editar/{id}";
        }

        return "redirect:/filmes/{id}?img_updated";
    }

    @GetMapping(path = "/filmes/{id}", params = "avaliarFilme")
    public String avaliar(@PathVariable Long id,
                          @RequestParam("avaliarFilme") int nota,
                          Model model,
                          HttpServletRequest request) {
        filmeService.avaliar(id, nota);

        FilmeDto filmeDto = filmeService.findById(id);
        model.addAttribute("filmeDto", filmeDto);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: avaliacaoFilme";
        } else {
            return "filmes/filme";
        }
    }
}
