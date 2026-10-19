package br.com.projetoMotiva.model;

public class EquipeManutencao {

    private int id;
    private String nome;
    private String especialidade; // Ex: "Roçada Mecanizada" ou "Pulverização"
    private boolean disponivel;

    public EquipeManutencao(String nome, String especialidade, boolean disponivel) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.disponivel = disponivel;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecialidade() { return especialidade; }
    public void setEspecialidade(String especialidade) { this.especialidade = especialidade; }

    public boolean isDisponivel() { return disponivel; }
    public void setDisponivel(boolean disponivel) { this.disponivel = disponivel; }
}