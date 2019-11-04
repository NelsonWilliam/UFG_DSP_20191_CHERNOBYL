package br.com.nelsonwilliam.dsp20191.chernobyl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChernobylFilmesApplication {

    // NOTA: Durante testes, não tente rodar a aplicação diretamente pelo main.
    //       Use o comando "mvn spring-boot:run" na pasta do projeto (/Sistema/).
    //       Quando estiver rodando (quando parar de aparecer mensagens), abra o site em "http://localhost:8080/".
    //       Pra fechar, force a parada da execução do comando (CTRL+C no CMD do Windows).

    public static void main(String[] args) {
        SpringApplication.run(ChernobylFilmesApplication.class, args);
    }

}
