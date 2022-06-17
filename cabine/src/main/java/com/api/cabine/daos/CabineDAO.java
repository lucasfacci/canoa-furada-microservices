package com.api.cabine.daos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.cabine.beans.Cabine;

@Repository
public interface CabineDAO extends CrudRepository<Cabine, Integer> {

}
