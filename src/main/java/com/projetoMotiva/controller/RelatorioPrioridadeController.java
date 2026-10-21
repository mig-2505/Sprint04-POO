package com.projetoMotiva.controller;

import com.projetoMotiva.dto.RelatorioResponse;
import com.projetoMotiva.service.RelatorioPrioridadeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioPrioridadeController {

    private final RelatorioPrioridadeService service;

    public RelatorioPrioridadeController(RelatorioPrioridadeService service) {
        this.service = service;
    }

    // Gera o relatório com os trechos atuais e persiste no histórico
    @PostMapping
    public ResponseEntity<RelatorioResponse> gerar() {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.gerar());
    }

    @GetMapping
    public ResponseEntity<List<RelatorioResponse>> listarHistorico() {
        return ResponseEntity.ok(service.listarHistorico());
    }

    // Ex.: /api/relatorios/periodo?inicio=2026-10-01&fim=2026-10-31
    @GetMapping("/periodo")
    public ResponseEntity<List<RelatorioResponse>> buscarPorPeriodo(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        return ResponseEntity.ok(service.buscarPorPeriodo(inicio, fim));
    }
}