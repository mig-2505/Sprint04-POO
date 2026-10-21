package com.projetoMotiva.dto;

import com.projetoMotiva.model.TrechoRodovia;

public record TrechoResponse(
        Long id,
        int km,
        double alturaVegetacao,
        double umidade,
        boolean possuiSensor
) {
    public static TrechoResponse from(TrechoRodovia trecho) {
        return new TrechoResponse(
                trecho.getId(),
                trecho.getKm(),
                trecho.getAlturaVegetacao(),
                trecho.getUmidade(),
                trecho.isPossuiSensor()
        );
    }
}