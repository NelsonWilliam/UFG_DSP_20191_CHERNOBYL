package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.PessoaDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.PessoaService;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions.InvalidFieldException;
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
import java.util.List;
import java.util.Objects;

@Transactional
@Controller
public class PessoasController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping("/pessoas")
    public String getAll(Model model) {
        List<PessoaDto> pessoas = pessoaService.findAll();
        model.addAttribute("pessoas", pessoas);
        return "pessoas/pessoas";
    }

    @GetMapping("/pessoas/{id}")
    public String getById(@PathVariable Long id, Model model) {
        PessoaDto pessoaDto = pessoaService.findById(id);
        model.addAttribute("pessoaDto", pessoaDto);
        return "pessoas/pessoa";
    }

    @GetMapping("/admin/pessoas/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        pessoaService.deleteById(id);
        return "redirect:/pessoas?deleted";
    }

    @GetMapping("/admin/pessoas/editar")
    public String editarNovo(Model model) {
        PessoaDto pessoaDto = new PessoaDto();
        model.addAttribute("pessoaDto", pessoaDto);
        return "pessoas/editar";
    }

    @GetMapping("/admin/pessoas/editar/{id}")
    public String editarExistente(@PathVariable Long id, Model model) {
        PessoaDto pessoaDto = pessoaService.findById(id);
        model.addAttribute("pessoaDto", pessoaDto);
        return "pessoas/editar";
    }

    @PostMapping("/admin/pessoas/editar/enviar")
    public String editarEnviar(@Valid PessoaDto pessoaDto, BindingResult bindingResult) {
        try {
            pessoaService.validar(pessoaDto);
        } catch (InvalidFieldException ife) {
            bindingResult.rejectValue(ife.getField(), "error.pessoaDto", ife.getReason());
        }

        if (bindingResult.hasErrors()) {
            return "pessoas/editar";
        }

        Long idAnterior = pessoaDto.getId();
        Long idNovo = pessoaService.salvar(pessoaDto).getId();
        if (Objects.equals(idAnterior, idNovo)) {
            return "redirect:/pessoas/" + idNovo + "?updated";
        } else {
            return "redirect:/pessoas?created";
        }
    }

    @PostMapping("/admin/pessoas/{id}/alterar_imagem")
    public String alterarImagem(@PathVariable Long id, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("err", "Selecione um arquivo!");
            return "redirect:/admin/pessoas/editar/{id}";
        }

        try {
            pessoaService.salvarImagem(id, file);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("err", "Não foi possível realizar upload: " + e.getMessage());
            return "redirect:/admin/pessoas/editar/{id}";
        }

        return "redirect:/pessoas/{id}?img_updated";
    }
}
