package com.api.fatura.daos;

import org.springframework.data.repository.CrudRepository;

import com.api.fatura.beans.Fatura;

public interface FaturaDAO extends CrudRepository<Fatura, Integer> {

}
