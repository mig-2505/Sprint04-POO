package com.projetoMotiva.dto;

import com.projetoMotiva.model.EquipeManutencao;

public record EquipeResponse(
        Long id,
        String nome,
        String especialidade,
        boolean disponivel
) {
    public static EquipeResponse from(EquipeManutencao equipe) {
        return new EquipeResponse(
                equipe.getId(),
                equipe.getNome(),
                equipe.getEspecialidade(),
                equipe.isDisponivel()
        );
    }
}