package com.projetoMotiva.dto;

import com.projetoMotiva.model.IntervencaoOperacional;

import java.time.LocalDateTime;

public record IntervencaoResponse(
        Long id,
        String tipo,
        int kmTrecho,
        String status,
        LocalDateTime dataRegistro,
        String servico
) {
    public static IntervencaoResponse from(IntervencaoOperacional intervencao) {
        return new IntervencaoResponse(
                intervencao.getId(),
                intervencao.getTipo(),
                intervencao.getKmTrecho(),
                intervencao.getStatus(),
                intervencao.getDataRegistro(),
                intervencao.executarServico() // polimorfismo: cada subclasse responde de um jeito
        );
    }
}