package br.com.projetoMotiva.dao;

import br.com.projetoMotiva.db.ConexaoBanco;
import br.com.projetoMotiva.model.EquipeManutencao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeManutencaoDAO {

    private static final String SQL_INSERIR = "INSERT INTO tb_equipe_manutencao (nome, especialidade, disponivel) VALUES (?, ?, ?)";
    private static final String SQL_BUSCAR_POR_ID = "SELECT * FROM tb_equipe_manutencao WHERE id = ?";
    private static final String SQL_LISTAR_TODAS = "SELECT * FROM tb_equipe_manutencao";
    private static final String SQL_ATUALIZAR = "UPDATE tb_equipe_manutencao SET nome = ?, especialidade = ?, disponivel = ? WHERE id = ?";
    private static final String SQL_DELETAR = "DELETE FROM tb_equipe_manutencao WHERE id = ?";

    public EquipeManutencaoDAO() {}

    public void inserir(EquipeManutencao equipe) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_INSERIR, new String[]{"ID"});

            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getEspecialidade());
            stmt.setInt(3, equipe.isDisponivel() ? 1 : 0);

            if (stmt.executeUpdate() > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    equipe.setId(rs.getInt(1));
                }
                System.out.println("Equipe inserida com sucesso! ID gerado: " + equipe.getId());
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir equipe: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    public EquipeManutencao buscarPorId(int id) {
        EquipeManutencao equipe = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_BUSCAR_POR_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                equipe = new EquipeManutencao(
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getInt("disponivel") == 1
                );
                equipe.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar equipe por ID: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return equipe;
    }

    public List<EquipeManutencao> listarTodas() {
        List<EquipeManutencao> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_LISTAR_TODAS);
            rs = stmt.executeQuery();

            while (rs.next()) {
                EquipeManutencao equipe = new EquipeManutencao(
                        rs.getString("nome"),
                        rs.getString("especialidade"),
                        rs.getInt("disponivel") == 1
                );
                equipe.setId(rs.getInt("id"));
                lista.add(equipe);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar equipes: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return lista;
    }

    public void atualizar(EquipeManutencao equipe) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConexaoBanco.getConexao();
            stmt = conn.prepareStatement(SQL_ATUALIZAR);

            stmt.setString(1, equipe.getNome());
            stmt.setString(2, equipe.getEspecialidade());
            stmt.setInt(3, equipe.isDisponivel() ? 1 : 0);
            stmt.setInt(4, equipe.getId());

            if (stmt.executeUpdate() > 0) {
                System.out.println("✅ Equipe atualizada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar equipe: " + e.getMessage());
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
                System.out.println("✅ Equipe deletada com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao deletar equipe: " + e.getMessage());
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }
}