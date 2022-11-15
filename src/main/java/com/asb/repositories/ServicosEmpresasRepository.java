package com.asb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asb.model.entity.ServicosEmpresas;

@Repository
public interface ServicosEmpresasRepository extends JpaRepository<ServicosEmpresas, Long> {

}
