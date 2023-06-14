package br.com.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dto.Cliente;
import br.com.repository.ClienteRepository;
import br.com.response.ResponseRest;
import br.com.response.ResponseRest.messageType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/cliente")
@Api(value = "Cadastra cliente", tags = { "cliente" })
public class ClienteController {

	@Autowired
	ClienteRepository repository;

	@Autowired
	BuscaCepController controller;

	@GetMapping
	@ResponseBody
	@ApiOperation(value = "Lista contas cadastradas.", notes = "Lista contas vinculadas as contas corrente.")
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Cliente> listaCliente() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Busca um cliente cadastrado.", notes = "Lista cliente baseado no id.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> buscarPorId(@PathVariable Long id, @ApiIgnore ResponseRest response) {
		if (validaSeExisteId(id).equals(false)) {
			response.setMessage("O id informado não existe.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(repository.findById(id));
	}

	@PostMapping
	@ResponseBody
	@ApiOperation(value = "Cadastra um novo cliente", notes = "cadastra cliente.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Cliente> inserir(@RequestBody Cliente cliente) throws Exception {
		return ResponseEntity.ok(repository.save(cliente));
	}

	@PutMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Atualiza UM registro", notes = "Atualiza registro relacionado a um cliente.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> atualiza(@RequestBody Cliente cliente, @ApiIgnore ResponseRest response) {
		Long id = cliente.getId();
		Optional<?> cliente2 = repository.findById(id);
		if (cliente2.isPresent()) {
			return ResponseEntity.ok(repository.save(cliente));
		}
		response.setMessage("O id informado não existe.");
		response.setType(messageType.ATENCAO);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	@ResponseBody
	@ApiOperation(value = "Deleta um registro", notes = "Deleta registro relacionado a um cliente.")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> deletar(@PathVariable Long id, @ApiIgnore ResponseRest response) {
		Optional<?> cliente2 = repository.findById(id);
		if (cliente2.isPresent()) {
			repository.deleteById(id);
			response.setMessage("Registro excluído com sucesso.");
			response.setType(messageType.SUCESSO);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			response.setMessage("O id informado não existe.");
			response.setType(messageType.ATENCAO);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
	}

	public Boolean validaSeExisteId(Long id) {
		Optional<Cliente> cliente = repository.findById(id);
		try {
			if (cliente.get().getId() != null) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}
}
