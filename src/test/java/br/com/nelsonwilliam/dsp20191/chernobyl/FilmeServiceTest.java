package br.com.nelsonwilliam.dsp20191.chernobyl;

import org.junit.Assert;
import org.junit.Test;

import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.FilmeService;

public class FilmeServiceTest {

	@Test(expected = Exception.class) //VERIFICAR
	public void avaliarFilmeNaoExistente() {
		//CONSTRUIR
		Long idFilme = (long) 12345;
		int nota = 2;

		//OPERAR
		FilmeService filmeService = new FilmeService();
		filmeService.avaliar(idFilme, nota);
	}
}
