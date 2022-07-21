package com.api.reservacabine.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.api.reservacabine.beans.Reserva;
import com.api.reservacabine.daos.ReservaCabineDAO;
import com.api.reservacabine.producers.ReservaCabineProducer;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class ReservaCabineController {

	@Autowired
	ReservaCabineDAO dao;
	
	@Autowired
	private ReservaCabineProducer producer;
	
	@GetMapping("/reservas")
	public ResponseEntity<Iterable<Reserva>> listarReservas() {
		return new ResponseEntity<Iterable<Reserva>>(dao.findAll(), HttpStatus.OK);
	}
	
	@GetMapping("/reservas/{id}")
	public ResponseEntity<Object> obterReserva(@PathVariable int id) {
		Optional<Reserva> optionalReservaCabineBean = dao.findById(id);
		
		if (optionalReservaCabineBean.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(optionalReservaCabineBean.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não encontrada.");
		}
	}
	
	@PostMapping("/reservas")
	public ResponseEntity<Object> registrarReserva(@Valid @RequestBody Reserva reserva) {
		final String url = "http://localhost:8080/cabines";
		JsonNode cabineSelecionada = null;
		
		RestTemplate restTemplate = new RestTemplate();
		String auth = "root:root";
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(auth.getBytes());
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    	headers.add("Authorization", encodedAuth);
        	HttpEntity<String> params = new HttpEntity<String>("parameters", headers);
        
		ResponseEntity<JsonNode> response = null;
		JsonNode cabines = null;
        
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, params, JsonNode.class);
		} catch (Exception e) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
		}

		cabines = response.getBody();
		ArrayList<JsonNode> cabinesIdeais = new ArrayList<JsonNode>();
		
		for (int i = 0; i < cabines.size(); i++) {
			JsonNode cabine = cabines.get(i);
			if (cabine.get("max_pessoas").asInt() >= reserva.getTotal_pessoas()) {
				cabinesIdeais.add(cabine);
			}
		}
		
		for (int i = cabinesIdeais.size() - 1; i >= 0; i--) {
			JsonNode cabine = cabinesIdeais.get(i);
			for (Reserva reservaExistente: dao.findAll()) {
				if (reservaExistente.getId_cabine() == cabine.get("id").asInt() && reservaExistente.getData().equals(reserva.getData())) {
					cabinesIdeais.removeIf(cabine::equals);
				}
			}
		}

		for (int i = 0; i < cabinesIdeais.size(); i++) {
			JsonNode cabine = cabinesIdeais.get(i);
			if (i == 0) {
				cabineSelecionada = cabine;
			}
			
			if (cabine.get("max_pessoas").asInt() < cabineSelecionada.get("max_pessoas").asInt()) {
				cabineSelecionada = cabine;
			}
		}
		
		if (cabineSelecionada == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não há cabines disponíveis.");
		} else {
			reserva.setId_cabine(cabineSelecionada.get("id").asInt());
			dao.save(reserva);
			producer.send(reserva.getId_cabine() + ";" + reserva.getData() + ";" + (reserva.getTotal_pessoas() * cabineSelecionada.get("preco_pessoa").asDouble()));
			return ResponseEntity.status(HttpStatus.OK).body("Reserva registrada com sucesso.");
		}
	}
	
	@DeleteMapping("/reservas/{id}")
	public ResponseEntity<Object> deletarCabine(@PathVariable int id) {
		Optional<Reserva> optionalReservaBean = dao.findById(id);
		if (optionalReservaBean.isPresent()) {
			dao.delete(optionalReservaBean.get());
			return ResponseEntity.status(HttpStatus.OK).body("Reserva deletada com sucesso.");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Reserva não encontrada.");
		}
	}
}
