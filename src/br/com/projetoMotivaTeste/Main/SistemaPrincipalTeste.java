package br.com.projetoMotivaTeste.Main;

import br.com.projetoMotiva.dao.*;
import br.com.projetoMotiva.db.ConexaoBanco;
import br.com.projetoMotiva.model.*;
import br.com.projetoMotiva.service.GerarRelatorio;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class SistemaPrincipalTeste {

    public static void main(String[] args) {

        System.out.println("1. TESTANDO CONEXÃO COM O BANCO");
        Connection conexao = ConexaoBanco.getConexao();

        System.out.println("2. TESTANDO CRUD DE EQUIPE DE MANUTENÇÃO");
        EquipeManutencaoDAO daoEquipe = new EquipeManutencaoDAO();

        // Inserir
        EquipeManutencao novaEquipe = new EquipeManutencao("Equipe Alpha", "Roçada Mecanizada", true);
        daoEquipe.inserir(novaEquipe);

        // Listar
        System.out.println("-> Listando Equipes:");
        List<EquipeManutencao> equipes = daoEquipe.listarTodas();
        for (EquipeManutencao eq : equipes) {
            System.out.println("   ID: " + eq.getId() + " | Nome: " + eq.getNome() + " | Disponível: " + eq.isDisponivel());
        }

        System.out.println("3. TESTANDO CRUD DE TRECHOS (Com Dados Reais)");
        TrechoRodoviaDAO daoTrecho = new TrechoRodoviaDAO();

        TrechoRodovia novoTrecho = new TrechoRodovia(100, 1.8, 80.0, true);
        daoTrecho.inserir(novoTrecho);

        System.out.println("-> Listando Trechos:");
        ArrayList<TrechoRodovia> trechosDoBanco = (ArrayList<TrechoRodovia>) daoTrecho.listarTodas();
        for (TrechoRodovia tr : trechosDoBanco) {
            System.out.println("   KM: " + tr.getKm() + " | Altura: " + tr.getAlturaVegetacao() + "m | Sensor: " + tr.isPossuiSensorIoT());
        }

        System.out.println("4. TESTANDO CRUD DE INTERVENÇÕES (Polimorfismo)");
        IntervencaoOperacionalDAO daoIntervencao = new IntervencaoOperacionalDAO();

        IntervencaoOperacional rocada = new RocadaMecanizada();
        rocada.setTipo("Roçada Mecanizada");
        rocada.setKmTrecho(100);
        rocada.setStatus("Pendente");
        daoIntervencao.inserir(rocada);

        System.out.println("5. GERANDO RELATÓRIO COM PERSISTÊNCIA");
        System.out.println("ATENÇÃO: O menu vai abrir abaixo. Digite '1' para testar a gravação e depois '0' para sair.");

        GerarRelatorio gerador = new GerarRelatorio();
        // Passamos a lista de trechos que puxamos do banco de dados na Etapa 3!
        gerador.gerarRelatorio(trechosDoBanco);

        System.out.println("6. CONSULTANDO HISTÓRICO DE RELATÓRIOS");
        RelatorioPrioridadeDAO daoRelatorio = new RelatorioPrioridadeDAO();
        List<RelatorioPrioridade> relatorios = daoRelatorio.listarTodas();

        for (RelatorioPrioridade rel : relatorios) {
            System.out.println("   ID: " + rel.getId());
            System.out.println("   Resumo: " + rel.getResumo());
            System.out.println("   [Urgentes: " + rel.getQtUrgente() + " | Críticos: " + rel.getQtCritico() + " | Normais: " + rel.getQtNormal() + "]\n");
        }

        System.out.println("7. FECHANDO CONEXÃO");
        ConexaoBanco.fechar(conexao);
        System.out.println("✅ TESTE FINALIZADO COM SUCESSO!");
    }
}