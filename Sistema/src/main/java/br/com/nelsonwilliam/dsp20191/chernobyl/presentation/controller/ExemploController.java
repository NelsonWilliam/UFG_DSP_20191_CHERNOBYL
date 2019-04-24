package br.com.nelsonwilliam.dsp20191.chernobyl.presentation.controller;

import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.entity.ExemploEntity;
import br.com.nelsonwilliam.dsp20191.chernobyl.presentation.business.service.ExemploService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExemploController {

    // A anotação @Autowired faz com que o valor dessa variável seja magicamente definido pelo
    // Spring. Isso só funciona com variáveis cujo tipo seja um bean gerenciado pelo Spring
    // (em geral, classes que possuem anotações @Component, @Service, @Repository ou @Entity.)
    @Autowired
    private ExemploService exemploService;

    // Define o que deve ser retornado ao enviar um GET para a URL "www.nossoservidor.com"
    @GetMapping("/")
    public String index(Model model) {
        // O valor retornado é o caminho pro template da página a ser exibida.
        // Não precisa incluir a extensão e começa a partir da pasta src/main/resources/templates.
        return "index";
    }

    // Define o que deve ser retornado ao enviar um GET para a URL "www.nossoservidor.com/entities"
    @GetMapping("/entities")
    public String listEntities(Model model) {
        // O objeto Model que é passado como parâmetro é usado para armazenar os dados que a view
        // irá exibir. Esses dados são "atributos" e são armazenados como pares chave-valor.
        // A engine de templates da view (no nosso caso, Thymeleaf) poderá acessar esses atributos
        // na hora de gerar o HTML.
        model.addAttribute("entidades", exemploService.findAll());

        // O valor retornado é o caminho pro template da página a ser exibida.
        // Não precisa incluir a extensão e começa a partir da pasta src/main/resources/templates.
        return "entities";
    }

    // Define o que deve ser retornado ao enviar um GET para a URL "www.nossoservidor
    // .com/api/entity/{nome}"
    // Nesse caso o {nome} pode ser qualquer coisa. O valor é passado como parâmetro pro método.
    @GetMapping("/api/entity/{nome}")
    // Define que não deve retornar uma página web, e sim um conteúdo qualquer.
    @ResponseBody
    public ExemploEntity getEntityByName(@PathVariable String nome) {
        // Esse método é um exemplo de REST API que retorna o objeto com determinado id. Como não
        // tem view, não tem model também.

        // Quando se retorna um objeto qualquer, o Spring já converte pra JSON.
        return exemploService.findFirstByNome(nome);
    }

    @DeleteMapping("/api/entity/{id}")
    public void deleteEntity(@PathVariable long id) {
        exemploService.deleteById(id);
    }

    @PostMapping("/entities")
    public void createEntity() {

    }

}
