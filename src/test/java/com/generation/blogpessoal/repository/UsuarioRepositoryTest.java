package com.generation.blogpessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import com.generation.blogpessoal.model.Usuario;

//indica que a classe UsuarioRepositoryTest é uma classe de test, é que esse test será rodado em uma porta aleatoria local no meu computador(desde que ela não esteja já sendo usada)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

//indica que o teste aser feito e um teste unitario (por classe) 
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioRepositoryTest {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	// inserindo usuarios no meu banco de dados h2 , para que eu possa testar funçoes por nome e email
	@BeforeAll
	void start() {
		
		usuarioRepository.deleteAll();
		
		usuarioRepository.save(new Usuario(0L,"João da Silva", "joao@gmail.com.br","13465278","https://upload.wikimedia.org/"));
		
		usuarioRepository.save(new Usuario(0L,"Jessica Silva", "Jk@gmail.com.br","10987654","https://upload.wikimedia.org/g"));
		
		usuarioRepository.save(new Usuario(0L,"Bruno Silva", "Bp@gmail.com.br","03569412","https://upload.wikimedia.org/25"));
		
		usuarioRepository.save(new Usuario(0L,"Lucas Mendes", "lucas@gmail.com.br","78945612","https://upload.wikimedia.org/fr"));
		
	}
	
	//indica o inicio do teste 
	@Test
	
	//indica o nome do teste  
	@DisplayName("Retorna 1 usuario")
	public void deveRetornarUmUsuario() {
		//oq eu espero receber da api, buscando um usuario pelo se email
		Optional<Usuario> usuario = usuarioRepository.findByUsuario("lucas@gmail.com.br");
		
		//comparando se o que eu esperava receber, foi oque de fato meu teste trouxe
		assertTrue(usuario.get().getUsuario().equals("lucas@gmail.com.br"));
	}
	
	@Test
	@DisplayName("Retorna 3 usuario")
	public void deveRetornarTresUsuario() {
		
		List<Usuario> listaDeUsuario = usuarioRepository.findAllByNomeContainingIgnoreCase("Silva");
		assertEquals(3, listaDeUsuario.size());
		assertTrue(listaDeUsuario.get(0).getNome().equals("João da Silva"));
		assertTrue(listaDeUsuario.get(1).getNome().equals("Jessica Silva"));
		assertTrue(listaDeUsuario.get(2).getNome().equals("Bruno Silva"));
	}
	
	@AfterAll
	public void end() {
		usuarioRepository.deleteAll();
		
	}
}
