package com.api.fatura.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "tab_fatura")
public class Fatura {

	@Column(name = "id_fatura")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@NotNull(message = "Informe o id da cabine.")
	private int id_cabine;
	
	@NotNull(message = "Informe a data da reserva.")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private String data;
	
	@NotNull(message = "Informe o valor total da reserva da cabine.")
	private double total;

	public Fatura() {
		super();
	}

	public Fatura(int id, @NotNull(message = "Informe o id da cabine.") int id_cabine,
			@NotNull(message = "Informe a data da reserva.") String data,
			@NotNull(message = "Informe o valor total da reserva da cabine.") double total) {
		super();
		this.id = id;
		this.id_cabine = id_cabine;
		this.data = data;
		this.total = total;
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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
}
