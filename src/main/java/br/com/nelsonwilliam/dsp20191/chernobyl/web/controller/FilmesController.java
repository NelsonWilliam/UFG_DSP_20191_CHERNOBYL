package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.*;
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
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Transactional
@Controller
public class FilmesController {

    private static final String AJAX_HEADER_NAME = "X-Requested-With";
    private static final String AJAX_HEADER_VALUE = "XMLHttpRequest";

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @Autowired
    private ResenhaService resenhaService;

    @Autowired
    private AvaliacaoResenhaService avaliacaoResenhaService;

    @Autowired
    private TopicoService topicoService;

    @Autowired
    private AvaliacaoTopicoService avaliacaoTopicoService;

    @GetMapping("/filmes")
    public String verTodos(Model model) {
        // TODO: Adicionar algum tipo de paginação, já que isso seria inviável se tivesse muitos filmes.
        model.addAttribute("filmes", filmeService.findAllDto());
        return "filmes/filmes";
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/filmes/{id}")
    public String verUm(@PathVariable Long id, Model model, Principal principal) {
        UsuarioDto usuario = principal == null ? null : usuarioService.findDtoByLogin(principal.getName());
        Long idUsuario = null;

        FilmeDto filmeDto = filmeService.findDtoById(id);
        if (filmeDto == null)
            throw new IllegalArgumentException("Filme não encontrado");

        AvaliacaoFilmeDto avaliacaoFilmeDto = new AvaliacaoFilmeDto();
        if (usuario != null) {
            idUsuario = usuario.getId();
            AvaliacaoFilmeDto avaliacaoExistente = avaliacaoFilmeService.findByFilmeAndUsuario(id, usuario.getId());
            if (avaliacaoExistente != null)
                avaliacaoFilmeDto = avaliacaoExistente;
        }

        List<ResenhaDto> resenhasDto = resenhaService.getResenhasDtos(id, idUsuario);
        List<TopicoDto> topicosDto = topicoService.getTopicosDtos(id, idUsuario);

        model.addAttribute("filmeDto", filmeDto);
        model.addAttribute("minhaAvaliacao", avaliacaoFilmeDto.getGrauRadiacao());
        model.addAttribute("mediaAvaliacao", avaliacaoFilmeService.existePorFilme(filmeDto.getId()) ? avaliacaoFilmeService.calcularGrauPorFilme(filmeDto.getId()) : 0.0);
        model.addAttribute("resenhas", resenhasDto);
        model.addAttribute("topicos", topicosDto);
        model.addAttribute("novaResenha", new ResenhaDto());
        return "filmes/filme";
    }

    @GetMapping(path = "/filmes/{id}", params = "avaliarFilme")
    public String verUmAvaliarFilme(@PathVariable Long id,
                                    @RequestParam("avaliarFilme") int nota,
                                    Model model,
                                    HttpServletRequest request) {

        // Salva a avaliação
        UsuarioDto usuario = usuarioService.findDtoByLogin(request.getUserPrincipal().getName());
//        AvaliacaoFilme aval = new AvaliacaoFilme();
//        aval.setUsuario(usuarioService.findById(usuario.getId()));
//        aval.setFilme(filmeService.findById(id));
//        aval.setGrauRadiacao(nota);
//        avaliacaoFilmeService.save(aval);
        avaliacaoFilmeService.save(id, usuario.getId(), nota);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        model.addAttribute("minhaAvaliacao", nota);
        model.addAttribute("mediaAvaliacao", avaliacaoFilmeService.existePorFilme(id) ? avaliacaoFilmeService.calcularGrauPorFilme(id) : 0.0);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: avaliacaoFilme";
        } else {
            return "filmes/filme";
        }
    }

    @GetMapping(path = "/filmes/{id}", params = {"avaliarResenha", "positivo"})
    public String verUmAvaliarResenha(@PathVariable Long id,
                                      @RequestParam("avaliarResenha") long idResenha,
                                      @RequestParam("positivo") boolean positivo,
                                      Model model,
                                      HttpServletRequest request) {
        // Salva a avaliação
        UsuarioDto usuario = usuarioService.findDtoByLogin(request.getUserPrincipal().getName());
        AvaliacaoResenha aval = new AvaliacaoResenha();
        aval.setUsuario(usuarioService.findById(usuario.getId()));
        aval.setResenha(resenhaService.findById(idResenha));
        aval.setPositiva(positivo);
        aval = avaliacaoResenhaService.save(aval);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        List<ResenhaDto> resenhasDto = resenhaService.getResenhasDtos(id, usuario.getId());
        model.addAttribute("resenhas", resenhasDto);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: resenhas";
        } else {
            return "filmes/filme";
        }
    }

    @GetMapping(path = "/filmes/{id}", params = {"avaliarTopico", "positivo"})
    public String verUmAvaliarTopico(@PathVariable Long id,
                                     @RequestParam("avaliarTopico") long idTopico,
                                     @RequestParam("positivo") boolean positivo,
                                     Model model,
                                     HttpServletRequest request) {
        // Salva a avaliação
        UsuarioDto usuario = usuarioService.findDtoByLogin(request.getUserPrincipal().getName());
        AvaliacaoTopico aval = new AvaliacaoTopico();
        aval.setUsuario(usuarioService.findById(usuario.getId()));
        aval.setTopico(topicoService.findById(idTopico));
        aval.setPositiva(positivo);
        aval = avaliacaoTopicoService.save(aval);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        List<TopicoDto> topicosDtos = topicoService.getTopicosDtos(id, usuario.getId());
        model.addAttribute("topicos", topicosDtos);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: topicos";
        } else {
            return "filmes/filme";
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

        Long idAnterior = filmeDto.getId();
        Long idNovo = filmeService.save(filmeDto);

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

    @GetMapping("/filmes/{id}/editar-resenha")
    public String editarNovaResenha(@PathVariable Long id, Model model) {
        ResenhaDto resenhaDto = new ResenhaDto();
        resenhaDto.setIdFilme(id);
        model.addAttribute("resenhaDto", resenhaDto);
        return "filmes/editar-resenha";
    }

    @GetMapping("/filmes/{id}/editar-resenha/{idResenha}")
    public String editarResenhaExistente(@PathVariable Long id,
                                         @PathVariable Long idResenha,
                                         Model model,
                                         Principal principal) {

        model.addAttribute("resenhaDto", resenhaService.autorizarEditar(idResenha, principal));
        return "filmes/editar-resenha";
    }

    @PostMapping("/filmes/{id}/editar-resenha/enviar")
    public String editarResenhaEnviar(@PathVariable Long id,
                                      @Valid ResenhaDto resenhaDto,
                                      BindingResult bindingResult,
                                      Principal principal) {

        if (bindingResult.hasErrors()) {
            return "filmes/editar-resenha";
        }

        Long idAnterior = resenhaDto.getId();
        Long idNovo = resenhaService.save(resenhaDto, principal);

        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + id + "?updatedresenha=" + idAnterior;
        } else {
            return "redirect:/filmes/" + id + "?createdresenha=" + idNovo;
        }
    }

    @GetMapping("/filmes/{id}/excluir-resenha/{idResenha}")
    public String excluirResenha(@PathVariable Long id,
                                 @PathVariable Long idResenha,
                                 Principal principal) {

        ResenhaDto resenhaDto = resenhaService.autorizarEditar(idResenha, principal);
        resenhaService.deleteById(resenhaDto.getId());
        return "redirect:/filmes/" + id + "?deletedresenha=" + idResenha;
    }

    @GetMapping("/filmes/{id}/editar-topico")
    public String editarNovoTopico(@PathVariable Long id, Model model) {
        TopicoDto topicoDto = new TopicoDto();
        topicoDto.setIdFilme(id);
        model.addAttribute("topicoDto", topicoDto);
        return "filmes/editar-topico";
    }

    @GetMapping("/filmes/{id}/editar-topico/{idTopico}")
    public String editarTopicoExistente(@PathVariable Long id,
                                        @PathVariable Long idTopico,
                                        Model model,
                                        Principal principal) {

        model.addAttribute("topicoDto", topicoService.autorizarEditar(idTopico, principal));
        return "filmes/editar-topico";
    }

    @PostMapping("/filmes/{id}/editar-topico/enviar")
    public String editarTopicoEnviar(@PathVariable Long id,
                                     @Valid TopicoDto topicoDto,
                                     BindingResult bindingResult,
                                     Principal principal) {

        if (bindingResult.hasErrors()) {
            return "filmes/editar-topico";
        }

        Long idAnterior = topicoDto.getId();
        Long idNovo = topicoService.save(topicoDto, principal);

        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/filmes/" + id + "?updatedtopico=" + idAnterior;
        } else {
            return "redirect:/filmes/" + id + "?createdtopico=" + idNovo;
        }
    }

    @GetMapping("/filmes/{id}/excluir-topico/{idTopico}")
    public String excluirTopico(@PathVariable Long id,
                                @PathVariable Long idTopico,
                                Principal principal) {

        TopicoDto topicoDto = topicoService.autorizarEditar(idTopico, principal);
        topicoService.deleteById(topicoDto.getId());
        return "redirect:/filmes/" + id + "?deletedtopico=" + idTopico;
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
            saveImage(file, id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Não foi possível realizar upload: "
                    + e.getClass().getSimpleName() + " - " + e.getMessage());
            return "redirect:/admin/filmes/editar/{id}";
        }

        return "redirect:/filmes/{id}?img_updated";
    }

    public void saveImage(MultipartFile mFile, Long id) throws Exception {
        String newFile = mFile.getOriginalFilename();

        byte[] bytes = mFile.getBytes();
        Path path = Paths.get(newFile);
        Files.write(path, bytes);

        File f = new File(newFile);
        filmeService.alterarImagem(id, f);
        if (!f.delete()) {
            System.err.println("Couldn't delete " + f.getAbsolutePath());
        }
    }
}
