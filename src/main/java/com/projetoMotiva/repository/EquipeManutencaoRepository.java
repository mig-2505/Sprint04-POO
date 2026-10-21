package com.projetoMotiva.repository;

import com.projetoMotiva.model.EquipeManutencao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipeManutencaoRepository extends JpaRepository<EquipeManutencao, Long> {

    List<EquipeManutencao> findByDisponivel(boolean disponivel);

    List<EquipeManutencao> findByEspecialidade(String especialidade);
}