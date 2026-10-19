package br.com.projetoMotiva.service;

import br.com.projetoMotiva.dao.RelatorioPrioridadeDAO;
import br.com.projetoMotiva.mock.SensorMock;
import br.com.projetoMotiva.model.TrechoRodovia;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GerarRelatorio {

    public int opcao;
    Scanner sc = new Scanner(System.in);

    MotorPriorizacao motorPriorizacao = new MotorPriorizacao();
    RelatorioPrioridadeDAO relatorioDAO = new RelatorioPrioridadeDAO();

    public void gerarRelatorio(ArrayList<TrechoRodovia> trechos){
        do {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Gerar Relatorio Geral (e Salvar no Banco)");
            System.out.println("2. Gerar Relatorio Umidade");
            System.out.println("3. Analisar Trecho por KM");
            System.out.println("4. Testar Sensor Mock");
            System.out.println("0. Encerrar Sistema");
            System.out.print("Opção: ");

            if (!sc.hasNextInt()) {
                System.out.println("Por favor, digite um número válido.");
                sc.next();
                opcao = -1;
                continue;
            }
            opcao = sc.nextInt();

            switch (opcao) {

                case 1:
                    System.out.println("\n=== RELATORIO GERAL ===");

                    motorPriorizacao.analisarTrechos(trechos);

                    int qtUrgente = 0, qtCritico = 0, qtAtencao = 0, qtNormal = 0;

                    for (TrechoRodovia trecho : trechos) {
                        if (trecho.getAlturaVegetacao() > 1.5 && trecho.getUmidade() > 70) {
                            qtUrgente++; // Grama alta E crescendo rápido
                        } else if (trecho.getAlturaVegetacao() > 1.5) {
                            qtCritico++; // Apenas grama alta
                        } else if (trecho.getAlturaVegetacao() >= 1 && trecho.getAlturaVegetacao() <= 1.5) {
                            qtAtencao++; // Grama média
                        } else {
                            qtNormal++;  // Grama baixa
                        }
                    }

                    String resumo = "Análise concluída em " + trechos.size() + " trechos. " +
                            "Ação imediata necessária em " + (qtUrgente + qtCritico) + " trechos.";

                    System.out.println("\n[SISTEMA] Salvando histórico do relatório no banco de dados...");
                    relatorioDAO.salvarRelatorio(qtUrgente, qtCritico, qtAtencao, qtNormal, resumo);

                    break;

                case 2:
                    System.out.println("\n=== RELATORIO UMIDADE ===");
                    for (TrechoRodovia trecho : trechos){
                        System.out.print(trecho.getKm() + "km: ");
                        if (trecho.isPossuiSensorIoT()){
                            motorPriorizacao.calcularCrescimento(trecho);
                        } else {
                            System.out.println("Sem sensor IoT");
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o KM do trecho: ");
                    int km = sc.nextInt();
                    boolean encontrado = false;

                    for (TrechoRodovia trecho : trechos) {
                        if (trecho.getKm() == km) {
                            motorPriorizacao.analisarTrechos(new ArrayList<>(List.of(trecho)));
                            encontrado = true;
                            break;
                        }
                    }
                    if (!encontrado) {
                        System.out.println("Trecho não encontrado.");
                    }
                    break;

                case 4:
                    System.out.println("\n=== TESTE COM SENSOR MOCK ===");
                    SensorMock mock = new SensorMock(1.8, 75);
                    mock.transmitirDadosSensor();
                    System.out.println(mock.classificarCrescimento());
                    System.out.println(mock.simularEfeitoUmidade());
                    break;

                case 0:
                    System.out.println("Encerrando sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}