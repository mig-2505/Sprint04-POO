package com.projetoMotiva.repository;

import com.projetoMotiva.model.TrechoRodovia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrechoRodoviaRepository extends JpaRepository<TrechoRodovia, Long> {

    List<TrechoRodovia> findByKmGreaterThanEqual(int km);

    List<TrechoRodovia> findByAlturaVegetacaoGreaterThanEqual(double alturaVegetacao);

    List<TrechoRodovia> findByPossuiSensor(boolean possuiSensor);
}