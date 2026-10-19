package br.com.projetoMotiva.dao;

import br.com.projetoMotiva.db.ConexaoBanco;
import br.com.projetoMotiva.model.IntervencaoOperacional;
import br.com.projetoMotiva.model.Pulverizacao;
import br.com.projetoMotiva.model.RocadaMecanizada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IntervencaoOperacionalDAO {

    private static final String SQL_INSERIR = "INSERT INTO tb_intervencao_operacional (tipo, km_trecho, status) VALUES (?, ?, ?)";
    private static final String SQL_BUSCAR_POR_ID = "SELECT * FROM tb_intervencao_operacional WHERE id = ?";
    private static final String SQL_LISTAR_TODAS = "SELECT * FROM tb_intervencao_operacional";
    private static final String SQL_ATUALIZAR = "UPDATE tb_intervencao_operacional SET tipo = ?, km_trecho = ?, status = ? WHERE id = ?";
    private static final String SQL_DELETAR = "DELETE FROM tb_intervencao_operacional WHERE id = ?";

    public IntervencaoOperacionalDAO() {}

    public void inserir(IntervencaoOperacional intervencao) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"});

            stmt.setString(1, intervencao.getTipo());
            stmt.setInt(2, intervencao.getKmTrecho());
            stmt.setString(3, intervencao.getStatus());

            if (stmt.executeUpdate() > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    intervencao.setId(rs.getInt(1));
                }
                System.out.println("Intervenção inserida com sucesso! ID: " + intervencao.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir intervenção: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public IntervencaoOperacional buscarPorId(int id) {
        IntervencaoOperacional intervencao = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String tipo = rs.getString("tipo");

                // POO Aplicada: Instancia a classe filha correta
                if (tipo.equalsIgnoreCase("Pulverização")) {
                    intervencao = new Pulverizacao();
                } else {
                    intervencao = new RocadaMecanizada();
                }

                intervencao.setId(rs.getInt("id"));
                intervencao.setTipo(tipo);
                intervencao.setKmTrecho(rs.getInt("km_trecho"));
                intervencao.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar intervenção: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return intervencao;
    }

    public List<IntervencaoOperacional> listarTodas() {
        List<IntervencaoOperacional> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String tipo = rs.getString("tipo");
                IntervencaoOperacional intervencao;

                if (tipo.equalsIgnoreCase("Pulverização")) {
                    intervencao = new Pulverizacao();
                } else {
                    intervencao = new RocadaMecanizada();
                }

                intervencao.setId(rs.getInt("id"));
                intervencao.setTipo(tipo);
                intervencao.setKmTrecho(rs.getInt("km_trecho"));
                intervencao.setStatus(rs.getString("status"));

                lista.add(intervencao);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar intervenções: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public void atualizar(IntervencaoOperacional intervencao) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_ATUALIZAR);

            stmt.setString(1, intervencao.getTipo());
            stmt.setInt(2, intervencao.getKmTrecho());
            stmt.setString(3, intervencao.getStatus());
            stmt.setInt(4, intervencao.getId());

            if (stmt.executeUpdate() > 0) {
                System.out.println("Intervenção atualizada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar intervenção: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public void deletar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_DELETAR);
            stmt.setInt(1, id);

            if (stmt.executeUpdate() > 0) {
                System.out.println("Intervenção deletada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar intervenção: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}