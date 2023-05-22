package br.com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.BuscaCep;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import br.com.service.BuscaCepService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/buscaCeP")
@Api( value = "consultar Códigos de Endereçamento Postal", tags = { "BuscaCep" })
public class buscaCepController {
	
	@Autowired
	BuscaCepService service;
	
	@GetMapping
    @ResponseBody 
	@ApiOperation (
      value = "Busca cep.",
      notes = "consultar Códigos de Endereçamento Postal (CEP) do Brasil.")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> listaCliente(String cep, @ApiIgnore ResponseRest response) throws Exception{
		if(!validaSeExisteCep(service.getEndereco(cep).toString()) || service.getEndereco(cep).toString().length() != 8) {
			response.setMessage("Cep inválido.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<ResponseRest>(response, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.status(HttpStatus.OK).body(service.getEndereco(cep));
    }
	
	public Boolean validaSeExisteCep(String cep) throws Exception {
		BuscaCep buscaPorID = service.getEndereco(cep);
		try {
		if(buscaPorID != null) {
	     return true;
		}
		}catch(Exception e) {
		return false;
		}
		return false;
	}
}
