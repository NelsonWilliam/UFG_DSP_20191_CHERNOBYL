package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.ResenhaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.dto.TopicoDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.PapelEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.utils.Utilitario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
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


        List<ResenhaDto> resenhasDto = getResenhasDtos(id, usuario);
        List<TopicoDto> topicosDto = getTopicosDtos(id, usuario);

        model.addAttribute("filmeDto", filmeDto);
        model.addAttribute("minhaAvaliacao", avaliacaoFilme.getGrauRadiacao());
        model.addAttribute("mediaAvaliacao", avaliacaoFilmeService.existePorFilme(filmeDto.getId()) ? avaliacaoFilmeService.calcularGrauPorFilme(filmeDto.getId()) : 0.0);
        model.addAttribute("resenhas", resenhasDto);
        model.addAttribute("topicos", topicosDto);
        model.addAttribute("novaResenha", new ResenhaDto());
        return "filmes/filme";
    }

    private List<ResenhaDto> getResenhasDtos(@PathVariable Long id, Usuario usuario) {
        Collection<Resenha> resenhas = resenhaService.findByFilme(id);
        List<ResenhaDto> resenhasDto = new ArrayList<>();
        if (resenhas != null)
            for (Resenha res : resenhas) {
                AvaliacaoResenha avalResenha = usuario == null ? null : avaliacaoResenhaService.findByResenhaAndUsuario(res.getId(), usuario.getId());
                ResenhaDto dto = ResenhaDto.fromResenha(res, avalResenha, avaliacaoResenhaService);
                if (dto != null)
                    resenhasDto.add(dto);
            }
        return resenhasDto;
    }

    private List<TopicoDto> getTopicosDtos(@PathVariable Long id, Usuario usuario) {
        Collection<Topico> topicos = topicoService.findByFilme(id);
        List<TopicoDto> topicosDtos = new ArrayList<>();
        if (topicos != null)
            for (Topico top : topicos) {
                AvaliacaoTopico avalTop = usuario == null ? null : avaliacaoTopicoService.findByTopicoAndUsuario(top.getId(), usuario.getId());
                TopicoDto dto = TopicoDto.fromTopico(top, avalTop, avaliacaoTopicoService);
                if (dto != null)
                    topicosDtos.add(dto);
            }
        return topicosDtos;
    }

    @GetMapping(path = "/filmes/{id}", params = "avaliarFilme")
    public String verUmAvaliarFilme(@PathVariable Long id,
                                    @RequestParam("avaliarFilme") int nota,
                                    Model model,
                                    HttpServletRequest request) {

        // Salva a avaliação
        Usuario usuario = usuarioService.findByLogin(request.getUserPrincipal().getName());
        AvaliacaoFilme aval = new AvaliacaoFilme();
        aval.setUsuario(usuario);
        aval.setFilme(filmeService.findById(id));
        aval.setGrauRadiacao(nota);
        avaliacaoFilmeService.save(aval);

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
        Usuario usuario = usuarioService.findByLogin(request.getUserPrincipal().getName());
        AvaliacaoResenha aval = new AvaliacaoResenha();
        aval.setUsuario(usuario);
        aval.setResenha(resenhaService.findById(idResenha));
        aval.setPositiva(positivo);
        aval = avaliacaoResenhaService.save(aval);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        List<ResenhaDto> resenhasDto = getResenhasDtos(id, usuario);
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
        Usuario usuario = usuarioService.findByLogin(request.getUserPrincipal().getName());
        AvaliacaoTopico aval = new AvaliacaoTopico();
        aval.setUsuario(usuario);
        aval.setTopico(topicoService.findById(idTopico));
        aval.setPositiva(positivo);
        aval = avaliacaoTopicoService.save(aval);

        // Retorna o fragmento atualizado com o novo valor e a nova média
        List<TopicoDto> topicosDtos = getTopicosDtos(id, usuario);
        model.addAttribute("topicos", topicosDtos);
        if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "filmes/filme :: topicos";
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

        if (filmeDto.getImage() == null || filmeDto.getImage().isEmpty()) {
            filmeDto.setImage(Utilitario.getFilmImgString());
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
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Resenha resenha = resenhaService.findById(idResenha);
        if (resenha.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        model.addAttribute("resenhaDto", ResenhaDto.fromResenha(resenha));
        return "filmes/editar-resenha";
    }

    @PostMapping("/filmes/{id}/editar-resenha/enviar")
    public String editarResenhaEnviar(@PathVariable Long id,
                                      @Valid ResenhaDto resenhaDto,
                                      BindingResult bindingResult,
                                      Principal principal) {

        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Resenha resenha = resenhaDto.toResenha(filmeService, usuario);
        if (resenha.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        if (bindingResult.hasErrors()) {
            return "filmes/editar-resenha";
        }

        Long idAnterior = resenha.getId();
        resenha = resenhaService.save(resenha);
        Long idNovo = resenha.getId();

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
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Resenha resenha = resenhaService.findById(idResenha);
        if (resenha.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        resenhaService.deleteById(idResenha);
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
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Topico topico = topicoService.findById(idTopico);
        if (topico.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        model.addAttribute("topicoDto", TopicoDto.fromTopico(topico));
        return "filmes/editar-topico";
    }

    @PostMapping("/filmes/{id}/editar-topico/enviar")
    public String editarTopicoEnviar(@PathVariable Long id,
                                     @Valid TopicoDto topicoDto,
                                     BindingResult bindingResult,
                                     Principal principal) {

        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Topico topico = topicoDto.toTopico(filmeService, usuario);
        if (topico.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        if (bindingResult.hasErrors()) {
            return "filmes/editar-topico";
        }

        Long idAnterior = topico.getId();
        topico = topicoService.save(topico);
        Long idNovo = topico.getId();

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
        Usuario usuario = principal == null ? null : usuarioService.findByLogin(principal.getName());
        if (usuario == null) {
            throw new AccessDeniedException("Usuário não autorizado");
        }
        Topico topico = topicoService.findById(idTopico);
        if (topico.getAutor().getId() != usuario.getId() && !usuario.getPapeis().contains(PapelEnum.ADMIN)) {
            throw new AccessDeniedException("Usuário não autorizado");
        }

        topicoService.deleteById(idTopico);
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
