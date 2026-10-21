package com.projetoMotiva.repository;

import com.projetoMotiva.model.IntervencaoOperacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IntervencaoOperacionalRepository extends JpaRepository<IntervencaoOperacional, Long> {

    List<IntervencaoOperacional> findByTipo(String tipo);

    List<IntervencaoOperacional> findByStatus(String status);
}