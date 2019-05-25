package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Usuario;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.AvaliacaoFilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.PessoaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Objects;

@Controller
public class FilmesController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/filmes")
    public String verTodos(Model model) {
        // TODO: Adicionar algum tipo de paginação, já que isso seria inviável se tivesse muitos filmes.
        model.addAttribute("filmes", filmeService.findAllDto());
        return "filmes/filmes";
    }

    @GetMapping("/filmes/{id}")
    public String verUm(@PathVariable Long id, Model model, Principal principal) {
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());

        Filme filme = filmeService.findById(id);
        if (filme == null)
            throw new IllegalArgumentException("Filme não encontrado");
        FilmeDto filmeDto = FilmeDto.fromFilme(filme);

        AvaliacaoFilme avaliacaoFilme = new AvaliacaoFilme();
        if (usuario != null) {
            AvaliacaoFilme avaliacaoExistente = avaliacaoFilmeService.findByFilmeAndUsuario(id, usuario.getId());
            if (avaliacaoExistente != null)
                avaliacaoFilme = avaliacaoExistente;
        }

        model.addAttribute("filmeDto", filmeDto);
        model.addAttribute("minhaAvaliacao", avaliacaoFilme.getGrauRadiacao());
        model.addAttribute("mediaAvaliacao", avaliacaoFilmeService.existePorFilme(filmeDto.getId()) ? avaliacaoFilmeService.calcularGrauPorFilme(filmeDto.getId()) : 0.0);
        return "filmes/filme";
    }

    @GetMapping(path = "/filmes/{id}", params = "votar")
    public String verUmVotar(@PathVariable Long id,
                             @RequestParam("votar") int valor,
                             Model model,
                             HttpServletRequest request) {

        // Salva a avaliação
        Usuario usuario = usuarioService.findByLogin(request.getUserPrincipal().getName());
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setUsuario(usuario);
        aval.setFilme(filmeService.findById(id));
        aval.setGrauRadiacao(valor);
        avaliacaoFilmeService.save(aval);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        model.addAttribute("minhaAvaliacao", valor);
        model.addAttribute("mediaAvaliacao", avaliacaoFilmeService.existePorFilme(id) ? avaliacaoFilmeService.calcularGrauPorFilme(id) : 0.0);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: avaliacao";
        } else {
            return "filmes/filme";
        }
    }

    @GetMapping("/admin/filmes/editar")
    public String editarNovo(Model model) {
        model.addAttribute("filmeDto", new FilmeDto());
        return "filmes/editar";
    }

    @GetMapping("/admin/filmes/editar/{id}")
    public String editarExistente(@PathVariable Long id, Model model) {
        model.addAttribute("filmeDto", FilmeDto.fromFilme(filmeService.findById(id)));
        return "filmes/editar";
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "addAtor")
    public String editarAddAtor(FilmeDto filmeDto,
                                @RequestParam("addAtor") long id,
                                HttpServletRequest request) {

        if (!filmeDto.getIdAtores().contains(id))
            filmeDto.getIdAtores().add(id);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: idAtores";
        } else {
            return "filmes/editar";
        }
    }

    @PostMapping(path = {"/admin/filmes/editar", "/admin/filmes/editar/{id}"}, params = "removeAtor")
    public String editarRemoveAtor(FilmeDto filmeDto,
                                   @RequestParam("removeAtor") int index,
                                   HttpServletRequest request) {

        filmeDto.getIdAtores().remove(index);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/editar :: idAtores";
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
    public String editEnviar(@Valid FilmeDto filmeDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "filmes/editar";
        }

        Filme filme = filmeDto.toFilme(pessoaService);
        Long idAnterior = filme.getId();
        filme = filmeService.save(filme);
        Long idNovo = filme.getId();

        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + idNovo + "?updated";
        } else {
            return "redirect:/filmes?created";
        }
    }

}
