package com.api.cabine.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.cabine.beans.Cabine;
import com.api.cabine.daos.CabineDAO;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class CabineController {
	
	@Autowired
	CabineDAO dao;

	@GetMapping("/cabines")
	@Operation(summary = "Lista as cabines", description = "Obtêm a lista de todas as cabines", tags = { "cabines" })
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Cabines listadas", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Cabine.class)) }) })
	public ResponseEntity<Iterable<Cabine>> listarCabines() {
		return new ResponseEntity<Iterable<Cabine>>(dao.findAll(), HttpStatus.OK);
	}

	@GetMapping("/cabines/{id}")
	@Operation(summary = "Obtêm uma cabine", description = "Busca uma cabine por meio de seu id", tags = { "cabines" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cabine encontrada", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabine.class)) }),
			@ApiResponse(responseCode = "400", description = "Id da cabine inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cabine não encontrada", content = @Content) })
	public ResponseEntity<Object> obterCabine(@PathVariable int id) {
		Optional<Cabine> optionalCabineBean = dao.findById(id);

		if (optionalCabineBean.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalCabineBean.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cabine não encontrada.");
		}
	}

	@PostMapping("/cabines")
	@Operation(summary = "Registra uma cabine", description = "Registra uma nova cabine", tags = { "cabines" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cabine registrada", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabine.class)) }),
			@ApiResponse(responseCode = "400", description = "Erro ao registrar cabine", content = @Content) })
	public ResponseEntity<Object> registrarCabine(@Valid @RequestBody Cabine cabine) {
		dao.save(cabine);

		return ResponseEntity.status(HttpStatus.OK).body("Cabine registrada com sucesso.");
	}

	@PutMapping("/cabines/{id}")
	@Operation(summary = "Atualiza uma cabine", description = "Atualiza uma cabine por meio de seu id", tags = {
			"cabines" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cabine atualizada", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabine.class)) }),
			@ApiResponse(responseCode = "400", description = "Id da cabine inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cabine não encontrada", content = @Content) })
	public ResponseEntity<Object> atualizarCabine(@PathVariable int id, @RequestBody @Valid Cabine cabine) {
		Optional<Cabine> optionalCabineBean = dao.findById(id);
		if (optionalCabineBean.isPresent()) {
			Cabine cabineBean = optionalCabineBean.get();
			cabineBean.setMax_pessoas(cabine.getMax_pessoas());
			cabineBean.setPreco_pessoa(cabine.getPreco_pessoa());
			return ResponseEntity.status(HttpStatus.OK).body(dao.save(cabineBean));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cabine não encontrada.");
		}
	}

	@DeleteMapping("/cabines/{id}")
	@Operation(summary = "Deleta uma cabine", description = "Deleta uma cabine por meio de seu id", tags = {
			"cabines" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cabine deletada", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = Cabine.class)) }),
			@ApiResponse(responseCode = "400", description = "Id da cabine inválido", content = @Content),
			@ApiResponse(responseCode = "404", description = "Cabine não encontrada", content = @Content) })
	public ResponseEntity<Object> deletarCabine(@PathVariable int id) {
		Optional<Cabine> optionalCabineBean = dao.findById(id);
		if (optionalCabineBean.isPresent()) {
			dao.delete(optionalCabineBean.get());
			return ResponseEntity.status(HttpStatus.OK).body("Cabine deletada com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cabine não encontrada.");
		}
	}
}
