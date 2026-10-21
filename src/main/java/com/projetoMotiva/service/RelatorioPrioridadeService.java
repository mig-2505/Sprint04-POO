package com.projetoMotiva.service;

import com.projetoMotiva.dto.RelatorioResponse;
import com.projetoMotiva.exception.RegraNegocioException;
import com.projetoMotiva.model.NivelPrioridade;
import com.projetoMotiva.model.RelatorioPrioridade;
import com.projetoMotiva.model.TrechoRodovia;
import com.projetoMotiva.repository.RelatorioPrioridadeRepository;
import com.projetoMotiva.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
public class RelatorioPrioridadeService {

    private final RelatorioPrioridadeRepository relatorioRepository;
    private final TrechoRodoviaRepository trechoRepository;
    private final MotorPriorizacao motor;

    public RelatorioPrioridadeService(RelatorioPrioridadeRepository relatorioRepository,
                                      TrechoRodoviaRepository trechoRepository,
                                      MotorPriorizacao motor) {
        this.relatorioRepository = relatorioRepository;
        this.trechoRepository = trechoRepository;
        this.motor = motor;
    }

    // Gera o relatório a partir dos trechos atuais e já grava no histórico
    @Transactional
    public RelatorioResponse gerar() {
        List<TrechoRodovia> trechos = trechoRepository.findAll();
        if (trechos.isEmpty()) {
            throw new RegraNegocioException("Não há trechos cadastrados para gerar o relatório.");
        }

        Map<NivelPrioridade, Integer> contagem = motor.contar(trechos);
        int urgentes = contagem.get(NivelPrioridade.URGENTE);
        int criticos = contagem.get(NivelPrioridade.CRITICO);
        int atencao = contagem.get(NivelPrioridade.ATENCAO);
        int normais = contagem.get(NivelPrioridade.NORMAL);

        String resumo = motor.montarResumo(trechos.size(), urgentes, criticos);

        RelatorioPrioridade relatorio = new RelatorioPrioridade(urgentes, criticos, atencao, normais, resumo);
        return RelatorioResponse.from(relatorioRepository.save(relatorio));
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponse> listarHistorico() {
        return relatorioRepository.findAllByOrderByDataGeracaoDesc().stream()
                .map(RelatorioResponse::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<RelatorioResponse> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new RegraNegocioException("Informe as datas de início e fim.");
        }
        if (inicio.isAfter(fim)) {
            throw new RegraNegocioException("A data de início não pode ser posterior à data de fim.");
        }
        // O dia final entra inteiro (até 23:59:59)
        return relatorioRepository
                .findByDataGeracaoBetweenOrderByDataGeracaoDesc(inicio.atStartOfDay(), fim.atTime(LocalTime.MAX))
                .stream()
                .map(RelatorioResponse::from)
                .toList();
    }
}