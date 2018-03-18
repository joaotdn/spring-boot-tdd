package com.boot.cursotdd.servico.impl;

import java.util.Optional;

import com.boot.cursotdd.modelo.Pessoa;
import com.boot.cursotdd.modelo.Telefone;
import com.boot.cursotdd.repository.IPessoaRepository;
import com.boot.cursotdd.servico.IPessoaServico;
import com.boot.cursotdd.servico.exception.TelefoneNaoEncontradoException;
import com.boot.cursotdd.servico.exception.UnicidadeCpfException;
import com.boot.cursotdd.servico.exception.UnicidadeTelefoneException;

public class PessoaServicoImpl implements IPessoaServico {
	private final IPessoaRepository pessoaRepository;
	
	public PessoaServicoImpl(IPessoaRepository pessoaRepository) {
		this.pessoaRepository = pessoaRepository;
	}
	
	@Override
	public Pessoa salvar(Pessoa pessoa) throws Exception {
		Optional<Pessoa> optional = pessoaRepository.findByCpf(pessoa.getCpf());
		
		if (optional.isPresent()) {
			throw new UnicidadeCpfException();
		}
		
		final String ddd = pessoa.getTelefones().get(0).getDdd();
		final String telefone = pessoa.getTelefones().get(0).getNumero();
		optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(ddd, telefone);
		
		if (optional.isPresent()) {
			throw new UnicidadeTelefoneException();
		}
		
		return pessoaRepository.save(pessoa);
	}
	
	@Override
	public Pessoa buscarPeloTelefone(Telefone telefone) throws Exception {
		// o metodo retorna um optional
		// o get retorna a instancia dentro do optional
		Optional<Pessoa> optional = pessoaRepository.findByTelefoneDddAndTelefoneNumero(telefone.getDdd(), telefone.getNumero());
		return optional.orElseThrow(TelefoneNaoEncontradoException::new);
	}
}
