package com.projetoMotiva.dto;

import com.projetoMotiva.model.RelatorioPrioridade;

import java.time.LocalDateTime;

public record RelatorioResponse(
        Long id,
        int qtUrgente,
        int qtCritico,
        int qtAtencao,
        int qtNormal,
        String resumo,
        LocalDateTime dataGeracao
) {
    public static RelatorioResponse from(RelatorioPrioridade relatorio) {
        return new RelatorioResponse(
                relatorio.getId(),
                relatorio.getQtUrgente(),
                relatorio.getQtCritico(),
                relatorio.getQtAtencao(),
                relatorio.getQtNormal(),
                relatorio.getResumo(),
                relatorio.getDataGeracao()
        );
    }
}