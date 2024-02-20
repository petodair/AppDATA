package br.com.AppData.AppData.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.AppData.AppData.model.Conta;

public interface ContaRepository extends CrudRepository<Conta, String>{
	
	public Conta findById(long id);
	public Conta findByNome(String nome);
	
	@Query("select j from Conta j Where j.nome = :nome and j.senha = :senha")
	public Conta buscarConta(String nome, String senha);
	

}
