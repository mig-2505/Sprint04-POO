package com.projetoMotiva.repository;

import com.projetoMotiva.model.RelatorioPrioridade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RelatorioPrioridadeRepository extends JpaRepository<RelatorioPrioridade, Long> {

    List<RelatorioPrioridade> findAllByOrderByDataGeracaoDesc();

    List<RelatorioPrioridade> findByDataGeracaoBetweenOrderByDataGeracaoDesc(LocalDateTime inicio, LocalDateTime fim);
}