package com.projetoMotiva.controller;

import com.projetoMotiva.dto.IntervencaoRequest;
import com.projetoMotiva.dto.IntervencaoResponse;
import com.projetoMotiva.service.IntervencaoOperacionalService;
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
@RequestMapping("/api/intervencoes")
public class IntervencaoOperacionalController {

    private final IntervencaoOperacionalService service;

    public IntervencaoOperacionalController(IntervencaoOperacionalService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<IntervencaoResponse>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntervencaoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<IntervencaoResponse> criar(@Valid @RequestBody IntervencaoRequest dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntervencaoResponse> atualizar(@PathVariable Long id,
                                                         @Valid @RequestBody IntervencaoRequest dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas (derived queries do Spring Data)
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<IntervencaoResponse>> buscarPorTipo(@PathVariable String tipo) {
        return ResponseEntity.ok(service.buscarPorTipo(tipo));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<IntervencaoResponse>> buscarPorStatus(@PathVariable String status) {
        return ResponseEntity.ok(service.buscarPorStatus(status));
    }
}