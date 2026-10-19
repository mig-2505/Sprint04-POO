
package br.com.projetoMotiva.model;

import br.com.projetoMotiva.interfaces.MonitoravelViaIoT;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "TRECHO_RODOVIA")
public class TrechoRodovia implements MonitoravelViaIoT {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "trecho_rodovia_generator"
    )
    @SequenceGenerator(
            name = "trecho_rodovia_generator",
            sequenceName = "SUA_SEQUENCE_REAL",
            allocationSize = 1
    )
    @Column(name = "ID")
    private int id;

    @Column(name = "KM", nullable = false)
    private int km;

    @Column(name = "ALTURA_VEGETACAO", nullable = false)
    private double alturaVegetacao;

    @Column(name = "UMIDADE", nullable = false)
    private double umidade;

    @Column(name = "POSSUI_SENSOR_IOT", nullable = false)
    private boolean possuiSensorIoT;

    protected TrechoRodovia() {
    }

    public TrechoRodovia(
            int km,
            double alturaVegetacao,
            double umidade,
            boolean possuiSensorIoT) {

        this.setKm(km);
        this.setAlturaVegetacao(alturaVegetacao);
        this.setUmidade(umidade);
        this.setPossuiSensorIoT(possuiSensorIoT);
    }

    @Override
    public void transmitirDadosSensor() {
        System.out.println("Sensor IoT ativo no KM " + km + ". Altura atual da vegetação: " + alturaVegetacao + "m.");
    }

    public int getId() {
        return id;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public double getAlturaVegetacao() {
        return alturaVegetacao;
    }

    public void setAlturaVegetacao(double alturaVegetacao) {
        this.alturaVegetacao = alturaVegetacao;
    }

    public double getUmidade() {
        return umidade;
    }

    public void setUmidade(double umidade) {
        this.umidade = umidade;
    }

    public boolean isPossuiSensorIoT() {
        return possuiSensorIoT;
    }

    public void setPossuiSensorIoT(boolean possuiSensorIoT) {
        this.possuiSensorIoT = possuiSensorIoT;
    }
}
