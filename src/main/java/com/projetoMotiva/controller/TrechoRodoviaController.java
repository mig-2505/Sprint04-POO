package com.projetoMotiva.controller;

import com.projetoMotiva.dto.TrechoRequest;
import com.projetoMotiva.dto.TrechoResponse;
import com.projetoMotiva.service.TrechoRodoviaService;
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
@RequestMapping("/api/trechos")
public class TrechoRodoviaController {

    private final TrechoRodoviaService service;

    public TrechoRodoviaController(TrechoRodoviaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TrechoResponse>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrechoResponse> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<TrechoResponse> criar(@Valid @RequestBody TrechoRequest dados) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dados));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrechoResponse> atualizar(@PathVariable Long id,
                                                    @Valid @RequestBody TrechoRequest dados) {
        return ResponseEntity.ok(service.atualizar(id, dados));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Consultas (derived queries do Spring Data)
    @GetMapping("/km-minimo/{km}")
    public ResponseEntity<List<TrechoResponse>> buscarPorKmMinimo(@PathVariable int km) {
        return ResponseEntity.ok(service.buscarPorKmMinimo(km));
    }

    @GetMapping("/altura-minima/{altura}")
    public ResponseEntity<List<TrechoResponse>> buscarPorAlturaMinima(@PathVariable double altura) {
        return ResponseEntity.ok(service.buscarPorAlturaMinima(altura));
    }

    @GetMapping("/sensor/{possuiSensor}")
    public ResponseEntity<List<TrechoResponse>> buscarPorSensor(@PathVariable boolean possuiSensor) {
        return ResponseEntity.ok(service.buscarPorSensor(possuiSensor));
    }
}