package com.boot.cursotdd.servico;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.boot.cursotdd.modelo.Pessoa;
import com.boot.cursotdd.modelo.Telefone;
import com.boot.cursotdd.repository.IPessoaRepository;
import com.boot.cursotdd.servico.exception.TelefoneNaoEncontradoException;
import com.boot.cursotdd.servico.exception.UnicidadeCpfException;
import com.boot.cursotdd.servico.exception.UnicidadeTelefoneException;
import com.boot.cursotdd.servico.impl.PessoaServicoImpl;

@RunWith(SpringRunner.class)
public class PessoaServicoTest {
	private static final String NOME = "João Teodoro";
	private static final String CPF = "12345678912";
	private static final String DDD = "83";
	private static final String TELEFONE = "998033736";
	
	@MockBean
	private IPessoaRepository pessoaRepository;
	
	private IPessoaServico sut;
	
	private Pessoa pessoa;
	
	private Telefone telefone;
	
	@Before
	public void setUp() throws Exception {
		sut = new PessoaServicoImpl(pessoaRepository);
		telefone = new Telefone();
		telefone.setDdd(DDD);
		telefone.setNumero(TELEFONE);
		
		pessoa = new Pessoa();
		pessoa.setNome(NOME);
		pessoa.setCpf(CPF);
		pessoa.setTelefones(Arrays.asList(telefone));
		
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.empty());
	}
	
	@Test
	public void deve_salvar_pessoa_no_repositorio() throws Exception {
		sut.salvar(pessoa);
		verify(pessoaRepository).save(pessoa);
	}
	
	@Test(expected = UnicidadeCpfException.class)
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_cpf() throws Exception {
		when(pessoaRepository.findByCpf(CPF)).thenReturn(Optional.of(pessoa));
		sut.salvar(pessoa);
	}
	
	@Test(expected = UnicidadeTelefoneException.class)
	public void nao_deve_salvar_duas_pessoas_com_o_mesmo_numero_de_telefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, TELEFONE)).thenReturn(Optional.of(pessoa));
		sut.salvar(pessoa);
	}
	
	@Test
	public void deve_buscar_uma_pessoa_pelo_ddd_e_telefone() throws Exception {
		when(pessoaRepository.findByTelefoneDddAndTelefoneNumero(DDD, TELEFONE)).thenReturn(Optional.of(pessoa));
		
		Pessoa pessoaTeste = sut.buscarPeloTelefone(telefone);
		
		verify(pessoaRepository).findByTelefoneDddAndTelefoneNumero(DDD, TELEFONE);
		
		assertThat(pessoaTeste).isNotNull();
		assertThat(pessoaTeste.getNome()).isEqualTo(NOME);
		assertThat(pessoaTeste.getCpf()).isEqualTo(CPF);
	}
	
	@Test(expected = TelefoneNaoEncontradoException.class)
	public void deve_retornar_uma_excecao_quando_nao_encontrar_pessoa_pelo_ddd_e_telefone() throws Exception {
		sut.buscarPeloTelefone(telefone);
	}
}
