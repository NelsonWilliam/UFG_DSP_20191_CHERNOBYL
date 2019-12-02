package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.FilmeDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.dto.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.enums.CargoEnum;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.AvaliacaoFilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.FilmeService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.aplication.PessoaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.infrastructure.ImageHandler;
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

import javax.validation.Valid;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Transactional
@Controller
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;

    @Autowired
    private FilmeService filmeService;

    @Autowired
    private AvaliacaoFilmeService avaliacaoFilmeService;

    @GetMapping("/pessoas")
    public String verTodos(Model model) {
        model.addAttribute("pessoas", pessoaService.findAllDto());
        return "pessoas/pessoas";
    }

    @GetMapping("/pessoas/{id}")
    public String verUm(@PathVariable Long id, Model model) {
        PessoaDto pessoa = pessoaService.findByIdDto(id);
        if (pessoa == null)
            throw new IllegalArgumentException("Pessoa não encontrada");
        model.addAttribute("pessoaDto", pessoa);
        model.addAttribute("mediaRadiacao", getAvaliacoes(pessoa.getId()));
        return "pessoas/pessoa";
    }

    public String getAvaliacoes(Long idPessoa) {
        List<Double> avaliacoes = new ArrayList<>();
        CargoEnum cargo = pessoaService.findCargoById(idPessoa);

        //find filmes by user
        Collection<FilmeDto> filmes = null;
        if (cargo == CargoEnum.ATOR) {
            filmes = filmeService.findDtosByAtor(idPessoa);
        } else if (cargo == CargoEnum.DIRETOR) {
            filmes = filmeService.findDtosByDiretor(idPessoa);
        }

        //find all film scores
        for (FilmeDto filme : filmes) {
            double nota = avaliacaoFilmeService.calcularGrauPorFilme(filme.getId());
            avaliacoes.add(nota);
        }

        return pessoaService.calcularRadiacao(avaliacoes);

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

        if (pessoaDto.getImage() == null || pessoaDto.getImage().isEmpty()) {
            pessoaDto.setImage(ImageHandler.getPersonImgString());
        }

        Long idAnterior = pessoaDto.getId();
        Long idNovo = pessoaService.save(pessoaDto);

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

    @PostMapping("/admin/pessoas/{id}/alterar_imagem")
    public String alterarImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("err", "Selecione um arquivo!");
            return "redirect:/admin/pessoas/editar/{id}";
        }

        try {
            saveImage(file, id);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Não foi possível realizar upload: " + e.getMessage());
            return "redirect:/admin/pessoas/editar/{id}";
        }

        return "redirect:/pessoas/{id}?img_updated";
    }

    public void saveImage(MultipartFile mFile, Long id) throws Exception {
        String newFile = mFile.getOriginalFilename();

        byte[] bytes = mFile.getBytes();
        Path path = Paths.get(newFile);
        Files.write(path, bytes);

        File f = new File(newFile);
        pessoaService.alterarImagem(id, f);
        if (!f.delete()) {
            System.err.println("Couldn't delete " + f.getAbsolutePath());
        }
        
    }


}