package br.com.projetoMotiva.model;

public abstract class IntervencaoOperacional {

    private int id;
    private String tipo;      // Ex: "Pulverização" ou "Roçada"
    private int kmTrecho;
    private String status;    // Ex: "Pendente", "Concluído"

    public abstract void executarServico();

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getKmTrecho() { return kmTrecho; }
    public void setKmTrecho(int kmTrecho) { this.kmTrecho = kmTrecho; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}