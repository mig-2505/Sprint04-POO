package com.projetoMotiva.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(Pulverizacao.TIPO)
public class Pulverizacao extends IntervencaoOperacional {

    public static final String TIPO = "Pulverização";

    public Pulverizacao() {
        super(TIPO);
    }

    @Override
    public String executarServico() {
        return "Executando pulverização no KM " + getKmTrecho();
    }
}