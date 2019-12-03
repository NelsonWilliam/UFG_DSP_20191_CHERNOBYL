package br.com.nelsonwilliam.dsp20191.chernobyl.domain.services;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.*;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.values.enums.CargoEnum;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class AvaliacaoService {

    public double calcularMediaAvaliacao(Pessoa pessoa) {
        CargoEnum cargo = pessoa.getCargo();
        List<Filme> filmes = cargo == CargoEnum.ATOR
                ? pessoa.getFilmesAtuados()
                : pessoa.getFilmesDirigidos();

        double sum = 0.0;
        long count = 0;
        for (Filme filme : filmes) {
            count++;
            sum += calcularMediaAvaliacao(filme);
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public double calcularMediaAvaliacao(Filme filme) {
        Collection<AvaliacaoFilme> avaliacoes = filme.getAvaliacoes();

        double sum = 0.0;
        long count = 0;
        for (AvaliacaoFilme avaliacao : avaliacoes) {
            if (avaliacao.getNota() > 0) {
                count++;
                sum += avaliacao.getNota();
            }
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public double calcularMediaAprovacao(Resenha resenha) {
        Collection<AvaliacaoResenha> avaliacoes = resenha.getAvaliacoes();

        double sum = 0.0;
        long count = 0L;
        for (AvaliacaoResenha avaliacao : avaliacoes) {
            count++;
            sum += avaliacao.isPositiva() ? 1.0 : 0.0;
        }
        return count == 0 ? 0.0 : sum / count;
    }

    public double calcularMediaAprovacao(Topico topico) {
        Collection<AvaliacaoTopico> avaliacoes = topico.getAvaliacoes();

        double sum = 0.0;
        long count = 0L;
        for (AvaliacaoTopico avaliacao : avaliacoes) {
            count++;
            sum += avaliacao.isPositiva() ? 1.0 : 0.0;
        }
        return count == 0 ? 0.0 : sum / count;
    }
}
