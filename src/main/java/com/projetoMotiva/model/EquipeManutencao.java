package com.projetoMotiva.model;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_equipe_manutencao")
public class EquipeManutencao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "especialidade", nullable = false, length = 100)
    private String especialidade; // Ex: "Roçada Mecanizada" ou "Pulverização"

    @Convert(converter = BooleanNumericConverter.class)
    @Column(name = "disponivel", nullable = false)
    private boolean disponivel;

    // Construtor exigido pelo JPA
    protected EquipeManutencao() {
    }

    public EquipeManutencao(String nome, String especialidade, boolean disponivel) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.disponivel = disponivel;
    }

    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}