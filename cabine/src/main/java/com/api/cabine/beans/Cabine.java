package com.api.cabine.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;

@Entity(name = "tab_cabine")
@Schema(name = "Cabine", description = "Representa uma cabine")
public class Cabine {
	
	@Column(name = "id_cabine")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(name = "id", description = "Id da cabine", accessMode = AccessMode.READ_ONLY)
	private int id;

	@NotNull(message = "Informe o número máximo de pessoas suportado.")
	@Schema(name = "max_pessoas", description = "Número máximo de pessoas suportadas pela cabine", required = true, example = "5")
	private int max_pessoas;

	@NotNull(message = "Informe o preço por pessoa.")
	@Schema(name = "preco_pessoas", description = "Preço por pessoa na cabine", required = true, example = "100")
	private double preco_pessoa;

	public Cabine() {
		super();
	}

	public Cabine(int id, @NotNull(message = "Informe o número máximo de pessoas suportado.") int max_pessoas,
			@NotNull(message = "Informe o preço por pessoa.") double preco_pessoa) {
		super();
		this.id = id;
		this.max_pessoas = max_pessoas;
		this.preco_pessoa = preco_pessoa;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMax_pessoas() {
		return max_pessoas;
	}

	public void setMax_pessoas(int max_pessoas) {
		this.max_pessoas = max_pessoas;
	}

	public double getPreco_pessoa() {
		return preco_pessoa;
	}

	public void setPreco_pessoa(double preco_pessoa) {
		this.preco_pessoa = preco_pessoa;
	}
}
