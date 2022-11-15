package com.asb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.asb.model.entity.Monitoramento;


@Repository
public interface MonitoramentoRepository  extends JpaRepository<Monitoramento, Long>{

}
