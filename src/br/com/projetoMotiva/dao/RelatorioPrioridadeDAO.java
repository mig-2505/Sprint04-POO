package br.com.projetoMotiva.dao;

import br.com.projetoMotiva.db.ConexaoBanco;
import br.com.projetoMotiva.model.RelatorioPrioridade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioPrioridadeDAO {

    private static final String SQL_INSERIR = "INSERT INTO tb_relatorio_prioridade (qt_urgente, qt_critico, qt_atencao, qt_normal, resumo) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR_TODAS = "SELECT * FROM tb_relatorio_prioridade ORDER BY data_geracao DESC";

    public RelatorioPrioridadeDAO() {}

    // Método bônus exatamente como pedido no seu checklist!
    public void salvarRelatorio(int qtUrgente, int qtCritico, int qtAtencao, int qtNormal, String resumo) {
        RelatorioPrioridade relatorio = new RelatorioPrioridade(qtUrgente, qtCritico, qtAtencao, qtNormal, resumo);
        inserir(relatorio);
    }

    public void inserir(RelatorioPrioridade relatorio) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"});

            stmt.setInt(1, relatorio.getQtUrgente());
            stmt.setInt(2, relatorio.getQtCritico());
            stmt.setInt(3, relatorio.getQtAtencao());
            stmt.setInt(4, relatorio.getQtNormal());
            stmt.setString(5, relatorio.getResumo());

            if (stmt.executeUpdate() > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    relatorio.setId(rs.getInt(1));
                }
                System.out.println("✅ Relatório salvo no banco! ID gerado: " + relatorio.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar relatório: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public List<RelatorioPrioridade> listarTodas() {
        List<RelatorioPrioridade> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RelatorioPrioridade relatorio = new RelatorioPrioridade(
                        rs.getInt("qt_urgente"),
                        rs.getInt("qt_critico"),
                        rs.getInt("qt_atencao"),
                        rs.getInt("qt_normal"),
                        rs.getString("resumo")
                );
                relatorio.setId(rs.getInt("id"));
                lista.add(relatorio);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar relatórios: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }
}