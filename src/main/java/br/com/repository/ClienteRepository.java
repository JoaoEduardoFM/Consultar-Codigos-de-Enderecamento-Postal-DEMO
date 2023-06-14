package br.com.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.dto.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

}