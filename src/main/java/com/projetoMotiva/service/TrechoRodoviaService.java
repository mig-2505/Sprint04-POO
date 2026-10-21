package com.projetoMotiva.service;

import com.projetoMotiva.dto.TrechoRequest;
import com.projetoMotiva.dto.TrechoResponse;
import com.projetoMotiva.exception.RecursoNaoEncontradoException;
import com.projetoMotiva.exception.RegraNegocioException;
import com.projetoMotiva.model.TrechoRodovia;
import com.projetoMotiva.repository.TrechoRodoviaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TrechoRodoviaService {

    private final TrechoRodoviaRepository repository;

    public TrechoRodoviaService(TrechoRodoviaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TrechoResponse> listarTodos() {
        return repository.findAll().stream().map(TrechoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public TrechoResponse buscarPorId(Long id) {
        return TrechoResponse.from(buscarEntidade(id));
    }

    @Transactional
    public TrechoResponse criar(TrechoRequest dados) {
        validar(dados);
        TrechoRodovia trecho = new TrechoRodovia(
                dados.km(), dados.alturaVegetacao(), dados.umidade(), dados.possuiSensor());
        return TrechoResponse.from(repository.save(trecho));
    }

    @Transactional
    public TrechoResponse atualizar(Long id, TrechoRequest dados) {
        TrechoRodovia trecho = buscarEntidade(id);
        validar(dados);

        trecho.setKm(dados.km());
        trecho.setAlturaVegetacao(dados.alturaVegetacao());
        trecho.setUmidade(dados.umidade());
        trecho.setPossuiSensor(dados.possuiSensor());
        return TrechoResponse.from(repository.save(trecho));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<TrechoResponse> buscarPorKmMinimo(int km) {
        if (km < 0) {
            throw new RegraNegocioException("O quilômetro não pode ser negativo.");
        }
        return repository.findByKmGreaterThanEqual(km).stream().map(TrechoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TrechoResponse> buscarPorAlturaMinima(double altura) {
        if (!Double.isFinite(altura) || altura < 0) {
            throw new RegraNegocioException("A altura da vegetação deve ser um valor válido e não negativo.");
        }
        return repository.findByAlturaVegetacaoGreaterThanEqual(altura).stream().map(TrechoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<TrechoResponse> buscarPorSensor(boolean possuiSensor) {
        return repository.findByPossuiSensor(possuiSensor).stream().map(TrechoResponse::from).toList();
    }

    private TrechoRodovia buscarEntidade(Long id) {
        if (id == null || id <= 0) {
            throw new RegraNegocioException("O ID do trecho deve ser maior que zero.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Trecho não encontrado com ID: " + id));
    }

    // Regras da Sprint 1: valores físicos coerentes
    private void validar(TrechoRequest dados) {
        if (dados == null) {
            throw new RegraNegocioException("Os dados do trecho são obrigatórios.");
        }
        if (dados.km() == null || dados.km() < 0) {
            throw new RegraNegocioException("O quilômetro é obrigatório e não pode ser negativo.");
        }
        Double altura = dados.alturaVegetacao();
        if (altura == null || !Double.isFinite(altura) || altura < 0 || altura > 999.99) {
            throw new RegraNegocioException("A altura da vegetação deve estar entre 0 e 999.99.");
        }
        Double umidade = dados.umidade();
        if (umidade == null || !Double.isFinite(umidade) || umidade < 0 || umidade > 100) {
            throw new RegraNegocioException("A umidade deve estar entre 0 e 100.");
        }
        if (dados.possuiSensor() == null) {
            throw new RegraNegocioException("Informe se o trecho possui sensor IoT.");
        }
    }
}