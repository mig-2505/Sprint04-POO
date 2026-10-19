package br.com.projetoMotiva.dao;

import br.com.projetoMotiva.db.ConexaoBanco;
import br.com.projetoMotiva.model.TrechoRodovia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrechoRodoviaDAO {

    // QUERIES SQL
    private static final String SQL_INSERIR = "INSERT INTO tb_trecho_rodovia (km, altura_vegetacao, umidade, possui_sensor) VALUES (?, ?, ?, ?)";
    private static final String SQL_BUSCAR_POR_ID = "SELECT * FROM tb_trecho_rodovia WHERE id = ?";
    private static final String SQL_LISTAR_TODAS = "SELECT * FROM tb_trecho_rodovia";
    private static final String SQL_ATUALIZAR = "UPDATE tb_trecho_rodovia SET km = ?, altura_vegetacao = ?, umidade = ?, possui_sensor = ? WHERE id = ?";
    private static final String SQL_DELETAR = "DELETE FROM tb_trecho_rodovia WHERE id = ?";

    public TrechoRodoviaDAO() {
    }

    public void inserir(TrechoRodovia trecho){

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"});

            stmt.setInt(1, trecho.getKm());
            stmt.setDouble(2, trecho.getAlturaVegetacao());
            stmt.setDouble(3, trecho.getUmidade());
            stmt.setInt(4, trecho.isPossuiSensorIoT() ? 1 : 0);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    trecho.setId(rs.getInt(1));
                }
                System.out.println("Trecho inserido com sucesso! ID gerado: " + trecho.getId());
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            System.err.println("Erro ao inserir trecho: " + e.getMessage());

        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
            try { if (rs != null) conn.close(); } catch (SQLException e) { }
        }
}

    public TrechoRodovia buscarPorId(int id) {
        TrechoRodovia trechoEncontrado = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID);
            stmt.setInt(1, id);

            rs = stmt.executeQuery();

            if (rs.next()) {
                int km = rs.getInt("km");
                double altura = rs.getDouble("altura_vegetacao");
                double umidade = rs.getDouble("umidade");
                boolean possuiSensor = rs.getInt("possui_sensor") == 1;

                trechoEncontrado = new TrechoRodovia(km, altura, umidade, possuiSensor);
                trechoEncontrado.setId(rs.getInt("id"));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar trecho por ID: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }

        return trechoEncontrado;
    }

    public List<TrechoRodovia> listarTodas() {
        List<TrechoRodovia> listaTrechos = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_LISTAR_TODAS);

            rs = stmt.executeQuery();

            while (rs.next()) {
                int km = rs.getInt("km");
                double altura = rs.getDouble("altura_vegetacao");
                double umidade = rs.getDouble("umidade");
                boolean possuiSensor = rs.getInt("possui_sensor") == 1;

                TrechoRodovia trecho = new TrechoRodovia(km, altura, umidade, possuiSensor);
                trecho.setId(rs.getInt("id"));

                listaTrechos.add(trecho);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar trechos: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { }
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }

        return listaTrechos;
    }

    public void atualizar(TrechoRodovia trecho) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_ATUALIZAR);

            stmt.setInt(1, trecho.getKm());
            stmt.setDouble(2, trecho.getAlturaVegetacao());
            stmt.setDouble(3, trecho.getUmidade());
            stmt.setInt(4, trecho.isPossuiSensorIoT() ? 1 : 0);

            stmt.setInt(5, trecho.getId());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Trecho atualizado com sucesso!");
            } else {
                System.out.println("Nenhum trecho encontrado com esse ID para atualizar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar trecho: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
    }

    public void deletar(int id) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_DELETAR);

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Trecho deletado com sucesso!");
            } else {
                System.out.println("Nenhum trecho encontrado com esse ID para deletar.");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao deletar trecho: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) { }
            try { if (conn != null) conn.close(); } catch (SQLException e) { }
        }
    }
}
