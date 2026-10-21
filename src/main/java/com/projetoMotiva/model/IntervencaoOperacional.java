package com.projetoMotiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

/**
 * Classe abstrata da Sprint 2, agora mapeada com herança JPA (tabela única).
 * A coluna "tipo" decide qual subclasse é instanciada ao ler do banco:
 * "Pulverização" -> Pulverizacao | "Roçada Mecanizada" -> RocadaMecanizada.
 */
@Entity
@Table(name = "tb_intervencao_operacional")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.STRING, length = 50)
public abstract class IntervencaoOperacional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    // Mesma coluna do discriminador, só leitura (o JPA grava o valor sozinho)
    @Column(name = "tipo", insertable = false, updatable = false)
    private String tipo;

    @Column(name = "km_trecho", nullable = false)
    private int kmTrecho;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "data_registro")
    private LocalDateTime dataRegistro;

    protected IntervencaoOperacional() {
    }

    protected IntervencaoOperacional(String tipo) {
        this.tipo = tipo;
    }

    @PrePersist
    void definirDataRegistro() {
        if (dataRegistro == null) {
            dataRegistro = LocalDateTime.now();
        }
    }

    // Polimorfismo: cada subclasse descreve o serviço do seu jeito
    public abstract String executarServico();

    public Long getId() { return id; }

    public String getTipo() { return tipo; }

    public int getKmTrecho() { return kmTrecho; }
    public void setKmTrecho(int kmTrecho) { this.kmTrecho = kmTrecho; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getDataRegistro() { return dataRegistro; }
}