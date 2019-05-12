package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.nelsonwilliam.dsp20191.chernobyl.business.entity.Exemplo;
import br.com.nelsonwilliam.dsp20191.chernobyl.business.service.ExemploService;

@Controller
public class ExemploController {

    // A anotação @Autowired faz com que o valor dessa variável seja magicamente
    // definido pelo
    // Spring. Isso só funciona com variáveis cujo tipo seja um bean gerenciado pelo
    // Spring
    // (em geral, classes que possuem anotações @Component, @Service, @Repository ou
    // @Entity.)
    @Autowired
    private ExemploService exemploService;

    // Define o que deve ser retornado ao enviar um GET para a URL
    // "www.nossoservidor.com"
    @GetMapping("/exemplo/")
    public String index(Model model) {
        // O valor retornado é o caminho pro template da página a ser exibida.
        // Não precisa incluir a extensão e começa a partir da pasta
        // src/main/resources/templates.
        return "Exemplo/index";
    }

    // Define o que deve ser retornado ao enviar um GET para a URL
    // "www.nossoservidor.com/entities"
    @GetMapping("/exemplo/entity/list")
    public String listEntities(Model model) {
        // O objeto Model que é passado como parâmetro é usado para armazenar os dados
        // que a view
        // irá exibir. Esses dados são "atributos" e são armazenados como pares
        // chave-valor.
        // A engine de templates da view (no nosso caso, Thymeleaf) poderá acessar esses
        // atributos
        // na hora de gerar o HTML.
        model.addAttribute("entidades", exemploService.findAll());

        // O valor retornado é o caminho pro template da página a ser exibida.
        // Não precisa incluir a extensão e começa a partir da pasta
        // src/main/resources/templates.
        return "Exemplo/entities";
    }

    // Exibe uma entidade pelo nome
    @GetMapping("/exemplo/entity/nome/{nome}")
    public String edit(@PathVariable String nome, Model model) {
        Exemplo exemplo = exemploService.findFirstByNome(nome);
        if (exemplo == null)
            return "index";

        model.addAttribute("exemplo", exemplo);
        return "Exemplo/cadastrar";
    }

    // Edita uma entidade pelo id
    @GetMapping("/exemplo/entity/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        Exemplo exemplo = exemploService.findByid(id);
        model.addAttribute("exemplo", exemplo);
        return "Exemplo/cadastrar";
    }

    // Deleta uma entidade pelo id
    @GetMapping("/exemplo/entity/delete/{id}")
    public String deleteEntity(@PathVariable long id) {
        exemploService.deleteById(id);
        return "redirect:/exemplo/";
    }

    // Cadastra uma entidade e redireciona pro index
    @GetMapping("/exemplo/entity/cadastrar")
    public String cadastrar(Model model) {
        Exemplo exemplo = new Exemplo();
        model.addAttribute("exemplo", exemplo);
        return "Exemplo/cadastrar";
    }

    // Salva uma entidade e redireciona pro index
    @PostMapping("/exemplo/entity/salvar")
    public String createEntity(Exemplo exemplo) {
        exemploService.save(exemplo);
        return "redirect:/exemplo/";
    }

    // Exibe a tela de detalhes da entidade (tela de cadastro/edição)
    @GetMapping("/exemplo/cadastrar")
    public String cadastro() {
        return "cadastrar";
    }

}
