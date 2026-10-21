package com.projetoMotiva.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record IntervencaoRequest(

        @NotBlank(message = "O tipo é obrigatório (Pulverização ou Roçada Mecanizada).")
        @Size(max = 50, message = "O tipo deve ter no máximo 50 caracteres.")
        String tipo,

        @NotNull(message = "O km do trecho é obrigatório.")
        @PositiveOrZero(message = "O km do trecho não pode ser negativo.")
        Integer kmTrecho,

        @NotBlank(message = "O status é obrigatório (Pendente, Em andamento ou Concluído).")
        @Size(max = 50, message = "O status deve ter no máximo 50 caracteres.")
        String status
) {
}