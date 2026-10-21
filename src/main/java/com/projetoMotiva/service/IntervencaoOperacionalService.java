package com.projetoMotiva.service;

import com.projetoMotiva.dto.IntervencaoRequest;
import com.projetoMotiva.dto.IntervencaoResponse;
import com.projetoMotiva.exception.RecursoNaoEncontradoException;
import com.projetoMotiva.exception.RegraNegocioException;
import com.projetoMotiva.model.IntervencaoOperacional;
import com.projetoMotiva.model.Pulverizacao;
import com.projetoMotiva.model.RocadaMecanizada;
import com.projetoMotiva.repository.IntervencaoOperacionalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.List;

@Service
public class IntervencaoOperacionalService {

    private static final List<String> STATUS_VALIDOS = List.of("Pendente", "Em andamento", "Concluído");

    private final IntervencaoOperacionalRepository repository;

    public IntervencaoOperacionalService(IntervencaoOperacionalRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<IntervencaoResponse> listarTodas() {
        return repository.findAll().stream().map(IntervencaoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public IntervencaoResponse buscarPorId(Long id) {
        return IntervencaoResponse.from(buscarEntidade(id));
    }

    @Transactional
    public IntervencaoResponse criar(IntervencaoRequest dados) {
        validar(dados);

        // Polimorfismo: o tipo informado define qual subclasse é criada
        IntervencaoOperacional intervencao = instanciar(tipoCanonico(dados.tipo()));
        intervencao.setKmTrecho(dados.kmTrecho());
        intervencao.setStatus(statusCanonico(dados.status()));
        return IntervencaoResponse.from(repository.save(intervencao));
    }

    @Transactional
    public IntervencaoResponse atualizar(Long id, IntervencaoRequest dados) {
        IntervencaoOperacional intervencao = buscarEntidade(id);
        validar(dados);

        // O tipo define a subclasse da linha, então não pode ser trocado em um PUT
        if (!intervencao.getTipo().equals(tipoCanonico(dados.tipo()))) {
            throw new RegraNegocioException(
                    "Não é possível alterar o tipo da intervenção. Remova e crie uma nova.");
        }

        intervencao.setKmTrecho(dados.kmTrecho());
        intervencao.setStatus(statusCanonico(dados.status()));
        return IntervencaoResponse.from(repository.save(intervencao));
    }

    @Transactional
    public void deletar(Long id) {
        repository.delete(buscarEntidade(id));
    }

    @Transactional(readOnly = true)
    public List<IntervencaoResponse> buscarPorTipo(String tipo) {
        return repository.findByTipo(tipoCanonico(tipo)).stream().map(IntervencaoResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public List<IntervencaoResponse> buscarPorStatus(String status) {
        return repository.findByStatus(statusCanonico(status)).stream().map(IntervencaoResponse::from).toList();
    }

    private IntervencaoOperacional buscarEntidade(Long id) {
        if (id == null || id <= 0) {
            throw new RegraNegocioException("O ID da intervenção deve ser maior que zero.");
        }
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Intervenção não encontrada com ID: " + id));
    }

    private void validar(IntervencaoRequest dados) {
        if (dados == null) {
            throw new RegraNegocioException("Os dados da intervenção são obrigatórios.");
        }
        if (dados.tipo() == null || dados.tipo().isBlank()) {
            throw new RegraNegocioException("O tipo da intervenção é obrigatório.");
        }
        if (dados.kmTrecho() == null || dados.kmTrecho() < 0) {
            throw new RegraNegocioException("O km do trecho é obrigatório e não pode ser negativo.");
        }
        if (dados.status() == null || dados.status().isBlank()) {
            throw new RegraNegocioException("O status da intervenção é obrigatório.");
        }
    }

    private IntervencaoOperacional instanciar(String tipoCanonico) {
        if (Pulverizacao.TIPO.equals(tipoCanonico)) {
            return new Pulverizacao();
        }
        return new RocadaMecanizada();
    }

    // Aceita "pulverizacao", "Pulverização", "rocada mecanizada"... e devolve o texto oficial
    private String tipoCanonico(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new RegraNegocioException("O tipo da intervenção é obrigatório.");
        }
        String normalizado = normalizar(tipo);
        if (normalizado.equals(normalizar(Pulverizacao.TIPO))) {
            return Pulverizacao.TIPO;
        }
        if (normalizado.equals(normalizar(RocadaMecanizada.TIPO))) {
            return RocadaMecanizada.TIPO;
        }
        throw new RegraNegocioException(
                "Tipo inválido: '" + tipo + "'. Use 'Pulverização' ou 'Roçada Mecanizada'.");
    }

    private String statusCanonico(String status) {
        if (status == null || status.isBlank()) {
            throw new RegraNegocioException("O status da intervenção é obrigatório.");
        }
        String normalizado = normalizar(status);
        return STATUS_VALIDOS.stream()
                .filter(valido -> normalizar(valido).equals(normalizado))
                .findFirst()
                .orElseThrow(() -> new RegraNegocioException(
                        "Status inválido: '" + status + "'. Use: " + String.join(", ", STATUS_VALIDOS) + "."));
    }

    private String normalizar(String texto) {
        return Normalizer.normalize(texto.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase();
    }
}