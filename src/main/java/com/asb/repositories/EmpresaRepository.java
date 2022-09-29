package com.asb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asb.model.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>  {

}
