package com.projetoMotiva.model;

import com.projetoMotiva.interfaces.MonitoravelViaIoT;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_trecho_rodovia")
public class TrechoRodovia implements MonitoravelViaIoT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "km", nullable = false)
    private int km;

    @Column(name = "altura_vegetacao", nullable = false)
    private double alturaVegetacao;

    @Column(name = "umidade", nullable = false)
    private double umidade;

    @Convert(converter = BooleanNumericConverter.class)
    @Column(name = "possui_sensor", nullable = false)
    private boolean possuiSensor;

    // Construtor exigido pelo JPA
    protected TrechoRodovia() {
    }

    public TrechoRodovia(int km, double alturaVegetacao, double umidade, boolean possuiSensor) {
        this.km = km;
        this.alturaVegetacao = alturaVegetacao;
        this.umidade = umidade;
        this.possuiSensor = possuiSensor;
    }

    @Override
    public String transmitirDadosSensor() {
        if (!possuiSensor) {
            return "Trecho do KM " + km + " não possui sensor IoT.";
        }
        return "Sensor IoT ativo no KM " + km + ". Altura atual da vegetação: " + alturaVegetacao + "m.";
    }

    public Long getId() { return id; }

    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }

    public double getAlturaVegetacao() { return alturaVegetacao; }
    public void setAlturaVegetacao(double alturaVegetacao) { this.alturaVegetacao = alturaVegetacao; }

    public double getUmidade() { return umidade; }
    public void setUmidade(double umidade) { this.umidade = umidade; }

    public boolean isPossuiSensor() { return possuiSensor; }
    public void setPossuiSensor(boolean possuiSensor) { this.possuiSensor = possuiSensor; }
}