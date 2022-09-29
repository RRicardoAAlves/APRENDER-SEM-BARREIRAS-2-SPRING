package com.asb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asb.model.entity.DemandaEscola;

@Repository
public interface DemandaEscolaRepository extends JpaRepository<DemandaEscola, Long> {

}
