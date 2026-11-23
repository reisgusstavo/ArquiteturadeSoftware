package dao;

import util.PersistenciaException;
import model.Matricula;
import model.Pagamento;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOJdbc implements MatriculaDAO {

    @Override
    public void registrar(Matricula m, Pagamento p) {
        Connection conn = ConnectionFactory.getInstance().getConnection();
        try {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO matriculas (aluno_id, plano_id, extras, descricao, valor_total, data_inicio) "
                  + "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, m.getAlunoId());
                ps.setInt(2, m.getPlanoId());
                ps.setString(3, m.getExtras());
                ps.setString(4, m.getDescricao());
                ps.setBigDecimal(5, m.getValorTotal());
                ps.setDate(6, Date.valueOf(m.getDataInicio()));
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) m.setId(keys.getInt(1));
                }
            }
            p.setMatriculaId(m.getId());
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO pagamentos (matricula_id, metodo, valor, codigo_transacao, aprovado, data_hora) "
                  + "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
                ps.setInt(1, p.getMatriculaId());
                ps.setString(2, p.getMetodo().name());
                ps.setBigDecimal(3, p.getValor());
                ps.setString(4, p.getCodigoTransacao());
                ps.setBoolean(5, p.isAprovado());
                ps.setTimestamp(6, Timestamp.valueOf(p.getDataHora()));
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) p.setId(keys.getInt(1));
                }
            }
            conn.commit();
        } catch (SQLException e) {
            try { conn.rollback(); } catch (SQLException ignorada) { /* já falhou */ }
            throw new PersistenciaException("Erro ao registrar matrícula: " + e.getMessage(), e);
        } finally {
            try { conn.setAutoCommit(true); } catch (SQLException ignorada) { /* sem ação */ }
        }
    }

    @Override
    public List<Matricula> listarTodas() {
        String sql = "SELECT m.*, a.nome AS aluno_nome, pl.nome AS plano_nome FROM matriculas m "
                   + "JOIN alunos a ON a.id = m.aluno_id JOIN planos pl ON pl.id = m.plano_id "
                   + "ORDER BY m.id DESC";
        List<Matricula> lista = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Matricula m = new Matricula();
                m.setId(rs.getInt("id"));
                m.setAlunoId(rs.getInt("aluno_id"));
                m.setPlanoId(rs.getInt("plano_id"));
                m.setExtras(rs.getString("extras"));
                m.setDescricao(rs.getString("descricao"));
                m.setValorTotal(rs.getBigDecimal("valor_total"));
                m.setDataInicio(rs.getDate("data_inicio").toLocalDate());
                m.setAlunoNome(rs.getString("aluno_nome"));
                m.setPlanoNome(rs.getString("plano_nome"));
                lista.add(m);
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar matrículas: " + e.getMessage(), e);
        }
    }
}
