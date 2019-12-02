package br.com.nelsonwilliam.dsp20191.chernobyl.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

@Transactional
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "redirect:/filmes";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
