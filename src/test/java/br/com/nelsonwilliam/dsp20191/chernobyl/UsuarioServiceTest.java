package br.com.nelsonwilliam.dsp20191.chernobyl;

import org.junit.Test;

import br.com.nelsonwilliam.dsp20191.chernobyl.dtos.UsuarioDto;
import br.com.nelsonwilliam.dsp20191.chernobyl.service.application.UsuarioService;
import br.com.nelsonwilliam.dsp20191.chernobyl.web.exceptions.InvalidFieldException;

public class UsuarioServiceTest {

	@Test(expected = InvalidFieldException.class) //VERIFICAR
	public void validaUsuarioEmailDuplicado() throws InvalidFieldException {
		//CONSTRUIR
		
		//Cria um novo usuário
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("Natália Lopes");
		usuarioDto.setLogin("natalialopes");
		usuarioDto.setEmail("natalialopescig@gmail.com");
		usuarioDto.setSenha("123456");
		
		UsuarioService usuarioService = new UsuarioService();
		usuarioService.salvar(usuarioDto);
		
		//Cria objeto que será verificado
		UsuarioDto usuarioDtoVerifica = new UsuarioDto();
		usuarioDtoVerifica.setLogin("natalialopescig@gmail.com");

		//OPERAR
		usuarioService.validar(usuarioDtoVerifica); //Valida email
	}	
	
	@Test(expected = InvalidFieldException.class) //VERIFICAR
	public void validaUsuarioLoginDuplicado() throws InvalidFieldException {
		//CONSTRUIR
		
		//Cria um novo usuário
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setNome("Natália Lopes");
		usuarioDto.setLogin("natalialopes");
		usuarioDto.setEmail("natalialopescig@gmail.com");
		usuarioDto.setSenha("123456");
		
		UsuarioService usuarioService = new UsuarioService();
		usuarioService.salvar(usuarioDto);
		
		//Cria objeto que será verificado
		UsuarioDto usuarioDtoVerifica = new UsuarioDto();
		usuarioDtoVerifica.setLogin("natalialopes");

		//OPERAR
		usuarioService.validar(usuarioDtoVerifica); //Valida login
	}	
}