package br.com.nelsonwilliam.dsp20191.chernobyl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoFilme;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.AvaliacaoResenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Filme;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.entities.Resenha;
import br.com.nelsonwilliam.dsp20191.chernobyl.domain.services.AvaliacaoService;

public class AvaliacaoServiceTest {

	@Test
	public void deveCalcularMediaAvaliacao() {
		
	}
	
	@Test
	public void deveCalcularAvaliacaoMediaFilme1() {

		// CONSTRUIR
		Filme filme = new Filme();
		filme.setTitulo("O Fim do Mundo");

		List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

		AvaliacaoFilme avaliacaoUmFilme = new AvaliacaoFilme();
		avaliacaoUmFilme.setNota(2);
		avaliacoes.add(avaliacaoUmFilme);

		AvaliacaoFilme avaliacaoDoisFilme = new AvaliacaoFilme();
		avaliacaoDoisFilme.setNota(4);
		avaliacoes.add(avaliacaoDoisFilme);

		filme.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAvaliacao(filme);

		// VERIFICAR
		double mediaEsperada = 3;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}

	@Test
	public void deveCalcularAvaliacaoMediaFilme2() {

		// CONSTRUIR
		Filme filme = new Filme();
		filme.setTitulo("Amor e Preconceito");

		List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

		AvaliacaoFilme avaliacaoUmFilme = new AvaliacaoFilme();
		avaliacaoUmFilme.setNota(5);
		avaliacoes.add(avaliacaoUmFilme);

		AvaliacaoFilme avaliacaoDoisFilme = new AvaliacaoFilme();
		avaliacaoDoisFilme.setNota(5);
		avaliacoes.add(avaliacaoDoisFilme);

		AvaliacaoFilme avaliacaoTresFilme = new AvaliacaoFilme();
		avaliacaoTresFilme.setNota(5);
		avaliacoes.add(avaliacaoTresFilme);

		filme.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAvaliacao(filme);

		// VERIFICAR
		double mediaEsperada = 5;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}

	@Test
	public void deveCalcularAvaliacaoMediaFilme3() {
		// CONSTRUIR
		Filme filme = new Filme();
		filme.setTitulo("Jogos Vorazes");

		List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

		AvaliacaoFilme avaliacaoUmFilme = new AvaliacaoFilme();
		avaliacaoUmFilme.setNota(1);
		avaliacoes.add(avaliacaoUmFilme);

		AvaliacaoFilme avaliacaoDoisFilme = new AvaliacaoFilme();
		avaliacaoDoisFilme.setNota(2);
		avaliacoes.add(avaliacaoDoisFilme);

		filme.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAvaliacao(filme);

		// VERIFICAR
		double mediaEsperada = 1.5;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}

	@Test
	public void deveCalcularAvaliacaoMediaFilme4() {
		// CONSTRUIR
		Filme filme = new Filme();
		filme.setTitulo("O Fim do Mundo");

		List<AvaliacaoFilme> avaliacoes = new ArrayList<>();

		AvaliacaoFilme avaliacaoUmFilme = new AvaliacaoFilme();
		avaliacaoUmFilme.setNota(-5);
		avaliacoes.add(avaliacaoUmFilme);

		AvaliacaoFilme avaliacaoDoisFilme = new AvaliacaoFilme();
		avaliacaoDoisFilme.setNota(-3);
		avaliacoes.add(avaliacaoDoisFilme);

		filme.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAvaliacao(filme);

		// VERIFICAR
		double mediaEsperada = 0;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}

	@Test
	public void deveCalcularMediaAprovacaoResenha1() {
		// CONSTRUIR
		Resenha resenha = new Resenha();
		List<AvaliacaoResenha> avaliacoes = new ArrayList<>();

		AvaliacaoResenha avaliacaoResenha1 = new AvaliacaoResenha();
		avaliacaoResenha1.setPositiva(true);
		avaliacoes.add(avaliacaoResenha1);

		AvaliacaoResenha avaliacaoResenha2 = new AvaliacaoResenha();
		avaliacaoResenha2.setPositiva(true);
		avaliacoes.add(avaliacaoResenha2);

		resenha.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAprovacao(resenha);

		// VERIFICAR
		double mediaEsperada = 1;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}
	
	@Test
	public void deveCalcularMediaAprovacaoResenha2() {
		// CONSTRUIR
		Resenha resenha = new Resenha();
		List<AvaliacaoResenha> avaliacoes = new ArrayList<>();

		AvaliacaoResenha avaliacaoResenha1 = new AvaliacaoResenha();
		avaliacaoResenha1.setPositiva(true);
		avaliacoes.add(avaliacaoResenha1);

		AvaliacaoResenha avaliacaoResenha2 = new AvaliacaoResenha();
		avaliacaoResenha2.setPositiva(true);
		avaliacoes.add(avaliacaoResenha2);

		AvaliacaoResenha avaliacaoResenha3 = new AvaliacaoResenha();
		avaliacaoResenha3.setPositiva(false);
		avaliacoes.add(avaliacaoResenha3);
		
		resenha.setAvaliacoes(avaliacoes);

		// OPERAR
		AvaliacaoService avaliacaoService = new AvaliacaoService();
		double mediaObtida = avaliacaoService.calcularMediaAprovacao(resenha);

		// VERIFICAR
		double mediaEsperada = 0.6666666666666666;
		Assert.assertTrue(mediaObtida == mediaEsperada);
	}

}
