package com.projetoMotiva.controller;

import com.projetoMotiva.dto.EquipeRequest;
import com.projetoMotiva.dto.EquipeResponse;
import com.projetoMotiva.service.EquipeManutencaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipes")
public class EquipeManutencaoController {

    private final EquipeManutencaoService service;

    public EquipeManutencaoController(EquipeManutencaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EquipeResponse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipeResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EquipeResponse> criar(@Valid @RequestBody EquipeRequest dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipeResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody EquipeRequest dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas (derived queries do Spring Data)
    @GetMapping("/disponivel/{disponivel}")
    public ResponseEntity<List<EquipeResponse>> buscarPorDisponibilidade(@PathVariable boolean disponivel) {
        return ResponseEntity.ok(service.buscarPorDisponibilidade(disponivel));
    }

    @GetMapping("/especialidade/{especialidade}")
    public ResponseEntity<List<EquipeResponse>> buscarPorEspecialidade(@PathVariable String especialidade) {
        return ResponseEntity.ok(service.buscarPorEspecialidade(especialidade));
    }
}