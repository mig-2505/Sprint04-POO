package com.projetoMotiva.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(RocadaMecanizada.TIPO)
public class RocadaMecanizada extends IntervencaoOperacional {

    public static final String TIPO = "Roçada Mecanizada";

    public RocadaMecanizada() {
        super(TIPO);
    }

    @Override
    public String executarServico() {
        return "Executando roçada mecânica no KM " + getKmTrecho();
    }
}