package com.api.reservacabine.beans;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "tab_reserva")
public class Reserva {

	@Column(name = "id_reserva")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private int id_cabine;

	@NotNull(message = "Informe a data da reserva.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate data;

	@NotNull(message = "Informe o total de pessoas que ocuparão a cabine.")
	private int total_pessoas;

	public Reserva() {
		super();
	}

	public Reserva(int id, int id_cabine, @NotNull(message = "Informe a data da reserva.") LocalDate data,
			@NotNull(message = "Informe o total de pessoas que ocuparão a cabine.") int total_pessoas) {
		super();
		this.id = id;
		this.id_cabine = id_cabine;
		this.data = data;
		this.total_pessoas = total_pessoas;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_cabine() {
		return id_cabine;
	}

	public void setId_cabine(int id_cabine) {
		this.id_cabine = id_cabine;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public int getTotal_pessoas() {
		return total_pessoas;
	}

	public void setTotal_pessoas(int total_pessoas) {
		this.total_pessoas = total_pessoas;
	}
}
