package com.projetoMotiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_relatorio_prioridade")
public class RelatorioPrioridade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "qt_urgente", nullable = false)
    private int qtUrgente;

    @Column(name = "qt_critico", nullable = false)
    private int qtCritico;

    @Column(name = "qt_atencao", nullable = false)
    private int qtAtencao;

    @Column(name = "qt_normal", nullable = false)
    private int qtNormal;

    @Column(name = "resumo", nullable = false, length = 500)
    private String resumo;

    @Column(name = "data_geracao")
    private LocalDateTime dataGeracao;

    // Construtor exigido pelo JPA
    protected RelatorioPrioridade() {
    }

    public RelatorioPrioridade(int qtUrgente, int qtCritico, int qtAtencao, int qtNormal, String resumo) {
        this.qtUrgente = qtUrgente;
        this.qtCritico = qtCritico;
        this.qtAtencao = qtAtencao;
        this.qtNormal = qtNormal;
        this.resumo = resumo;
    }

    @PrePersist
    void definirDataGeracao() {
        if (dataGeracao == null) {
            dataGeracao = LocalDateTime.now();
        }
    }

    public Long getId() { return id; }
    public int getQtUrgente() { return qtUrgente; }
    public int getQtCritico() { return qtCritico; }
    public int getQtAtencao() { return qtAtencao; }
    public int getQtNormal() { return qtNormal; }
    public String getResumo() { return resumo; }
    public LocalDateTime getDataGeracao() { return dataGeracao; }
}