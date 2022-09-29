package com.asb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asb.model.entity.CadastroEscolaModel;




@Repository
public interface EscolaRepository extends JpaRepository<CadastroEscolaModel, Long> {

}
