package com.projetoMotiva.mock;

import com.projetoMotiva.interfaces.MonitoravelViaIoT;

public class SensorMock implements MonitoravelViaIoT {

    private final double alturaSimulada;
    private final double umidadeSimulada;

    public SensorMock(double alturaSimulada, double umidadeSimulada) {
        this.alturaSimulada = alturaSimulada;
        this.umidadeSimulada = umidadeSimulada;
    }

    @Override
    public String transmitirDadosSensor() {
        return "[MOCK] Sensor simulado - Altura capturada: " + alturaSimulada + "m";
    }

    public double getAlturaSimulada() {
        return alturaSimulada;
    }

    public double getUmidadeSimulada() {
        return umidadeSimulada;
    }

    public String classificarCrescimento() {
        if (alturaSimulada > 1.5) {
            return "Crescimento CRITICO - Roçada mecanizada necessária";
        } else if (alturaSimulada >= 1.0) {
            return "Crescimento MEDIO - Pulverização recomendada";
        }
        return "Crescimento NORMAL - Sem intervenção necessária";
    }

    public String simularEfeitoUmidade() {
        if (umidadeSimulada > 70) {
            return "[MOCK] Umidade alta - vegetação crescendo mais rápido";
        } else if (umidadeSimulada >= 40) {
            return "[MOCK] Umidade media - crescimento moderado";
        }
        return "[MOCK] Umidade baixa - crescimento lento";
    }
}