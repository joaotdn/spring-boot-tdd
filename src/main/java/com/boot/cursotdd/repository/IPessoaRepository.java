package com.boot.cursotdd.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.boot.cursotdd.modelo.Pessoa;

public interface IPessoaRepository extends JpaRepository<Pessoa, Long> {	
	Optional<Pessoa> findByCpf(String cpf);
	
	@Query("SELECT bean FROM Pessoa bean JOIN bean.telefones tele WHERE tele.ddd = :ddd AND tele.numero = :numero")
	Optional<Pessoa> findByTelefoneDddAndTelefoneNumero(@Param("ddd") String ddd, @Param("numero") String numero);
}
