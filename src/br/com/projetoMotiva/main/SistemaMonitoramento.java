package br.com.projetoMotiva.main;

import br.com.projetoMotiva.model.TrechoRodovia;
import br.com.projetoMotiva.service.GerarRelatorio;

import java.util.ArrayList;

public class SistemaMonitoramento {

    public static void main(String[] args) {

        GerarRelatorio relatorio = new GerarRelatorio();

        TrechoRodovia trecho1 = new TrechoRodovia(10, 2.0, 80, true);
        TrechoRodovia trecho2 = new TrechoRodovia(20, 1.2, 60, false);
        TrechoRodovia trecho3 = new TrechoRodovia(30, 0.8, 30, true);

        ArrayList<TrechoRodovia> trechos = new ArrayList<>();
        trechos.add(trecho1);
        trechos.add(trecho2);
        trechos.add(trecho3);

        relatorio.gerarRelatorio(trechos);

        }
    }
