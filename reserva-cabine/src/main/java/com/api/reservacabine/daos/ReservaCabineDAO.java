package com.api.reservacabine.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.reservacabine.beans.Reserva;

@Repository
public interface ReservaCabineDAO extends CrudRepository<Reserva, Integer> {

}
