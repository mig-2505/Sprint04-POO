package com.projetoMotiva.service;

import com.projetoMotiva.dto.EquipeRequest;
import com.projetoMotiva.dto.EquipeResponse;
import com.projetoMotiva.exception.RecursoNaoEncontradoException;
import com.projetoMotiva.exception.RegraNegocioException;
import com.projetoMotiva.model.EquipeManutencao;
import com.projetoMotiva.repository.EquipeManutencaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EquipeManutencaoService {

    private final EquipeManutencaoRepository repository;

    public EquipeManutencaoService(EquipeManutencaoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<EquipeResponse> listarTodas() {
        return repository.findAll().stream().map(EquipeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public EquipeResponse buscarPorId(Long id) {
        return EquipeResponse.from(buscarEntidade(id));
    }

    @Transactional
    public EquipeResponse criar(EquipeRequest dados) {
        validar(dados);
        EquipeManutencao equipe = new EquipeManutencao(
                dados.nome().trim(), dados.especialidade().trim(), dados.disponivel());
        return EquipeResponse.from(repository.save(equipe));
    }

    @Transactional
    public EquipeResponse atualizar(Long id, EquipeRequest dados) {
        EquipeManutencao equipe = buscarEntidade(id);
        validar(dados);

        equipe.setNome(dados.nome().trim());
        equipe.setEspecialidade(dados.especialidade().trim());
        equipe.setDisponivel(dados.disponivel());
        return EquipeResponse.from(repository.save(equipe));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<EquipeResponse> buscarPorDisponibilidade(boolean disponivel) {
        return repository.findByDisponivel(disponivel).stream().map(EquipeResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<EquipeResponse> buscarPorEspecialidade(String especialidade) {
        if (especialidade == null || especialidade.isBlank()) {
            throw new RegraNegocioException("A especialidade não pode ser vazia.");
        }
        return repository.findByEspecialidade(especialidade.trim()).stream().map(EquipeResponse::from).toList();
    }

    private EquipeManutencao buscarEntidade(Long id) {
        if (id == null || id <= 0) {
            throw new RegraNegocioException("O ID da equipe deve ser maior que zero.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Equipe não encontrada com ID: " + id));
    }

    private void validar(EquipeRequest dados) {
        if (dados == null) {
            throw new RegraNegocioException("Os dados da equipe são obrigatórios.");
        }
        if (dados.nome() == null || dados.nome().isBlank()) {
            throw new RegraNegocioException("O nome da equipe é obrigatório.");
        }
        if (dados.nome().trim().length() > 100) {
            throw new RegraNegocioException("O nome deve ter no máximo 100 caracteres.");
        }
        if (dados.especialidade() == null || dados.especialidade().isBlank()) {
            throw new RegraNegocioException("A especialidade é obrigatória.");
        }
        if (dados.especialidade().trim().length() > 100) {
            throw new RegraNegocioException("A especialidade deve ter no máximo 100 caracteres.");
        }
        if (dados.disponivel() == null) {
            throw new RegraNegocioException("Informe se a equipe está disponível.");
        }
    }
}