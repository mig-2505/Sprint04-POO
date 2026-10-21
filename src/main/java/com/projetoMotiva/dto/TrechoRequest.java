package com.projetoMotiva.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TrechoRequest(

        @NotNull(message = "O km é obrigatório.")
        @PositiveOrZero(message = "O km não pode ser negativo.")
        Integer km,

        @NotNull(message = "A altura da vegetação é obrigatória.")
        @DecimalMin(value = "0.0", message = "A altura da vegetação não pode ser negativa.")
        @DecimalMax(value = "999.99", message = "A altura da vegetação deve ser no máximo 999.99.")
        Double alturaVegetacao,

        @NotNull(message = "A umidade é obrigatória.")
        @DecimalMin(value = "0.0", message = "A umidade deve estar entre 0 e 100.")
        @DecimalMax(value = "100.0", message = "A umidade deve estar entre 0 e 100.")
        Double umidade,

        @NotNull(message = "Informe se o trecho possui sensor (true/false).")
        Boolean possuiSensor
) {
}