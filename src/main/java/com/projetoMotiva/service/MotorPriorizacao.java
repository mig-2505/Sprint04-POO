package com.projetoMotiva.service;

import com.projetoMotiva.model.NivelPrioridade;
import com.projetoMotiva.model.TrechoRodovia;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class MotorPriorizacao {

    public static final double ALTURA_ALTA_M = 1.5;
    public static final double ALTURA_MEDIA_M = 1.0;
    public static final double UMIDADE_ALTA = 70.0;

    public NivelPrioridade classificar(TrechoRodovia trecho) {
        double altura = trecho.getAlturaVegetacao();

        if (altura > ALTURA_ALTA_M && trecho.getUmidade() > UMIDADE_ALTA) {
            return NivelPrioridade.URGENTE;   // grama alta E crescendo rápido
        }
        if (altura > ALTURA_ALTA_M) {
            return NivelPrioridade.CRITICO;   // apenas grama alta
        }
        if (altura >= ALTURA_MEDIA_M) {
            return NivelPrioridade.ATENCAO;   // grama média
        }
        return NivelPrioridade.NORMAL;        // grama baixa
    }

    public Map<NivelPrioridade, Integer> contar(List<TrechoRodovia> trechos) {
        Map<NivelPrioridade, Integer> contagem = new EnumMap<>(NivelPrioridade.class);
        for (NivelPrioridade nivel : NivelPrioridade.values()) {
            contagem.put(nivel, 0);
        }
        for (TrechoRodovia trecho : trechos) {
            contagem.merge(classificar(trecho), 1, Integer::sum);
        }
        return contagem;
    }

    public String montarResumo(int totalTrechos, int urgentes, int criticos) {
        return "Análise concluída em " + totalTrechos + " trechos. "
                + "Ação imediata necessária em " + (urgentes + criticos) + " trechos.";
    }
}