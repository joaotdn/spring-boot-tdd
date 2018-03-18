package com.boot.cursotdd.servico;

import com.boot.cursotdd.modelo.Pessoa;
import com.boot.cursotdd.modelo.Telefone;

public interface IPessoaServico {
	
	Pessoa salvar(Pessoa pessoa) throws Exception;
	
	Pessoa buscarPeloTelefone(Telefone telefone) throws Exception;
}
