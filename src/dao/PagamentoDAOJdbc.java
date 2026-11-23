package dao;

import util.PersistenciaException;
import model.MetodoPagamento;
import model.Pagamento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PagamentoDAOJdbc implements PagamentoDAO {
    @Override
    public List<Pagamento> listarTodos() {
        String sql = "SELECT p.*, a.nome AS aluno_nome FROM pagamentos p "
                   + "JOIN matriculas m ON m.id = p.matricula_id JOIN alunos a ON a.id = m.aluno_id "
                   + "ORDER BY p.data_hora DESC, p.id DESC";
        List<Pagamento> lista = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getInstance().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Pagamento p = new Pagamento();
                p.setId(rs.getInt("id"));
                p.setMatriculaId(rs.getInt("matricula_id"));
                p.setMetodo(MetodoPagamento.valueOf(rs.getString("metodo")));
                p.setValor(rs.getBigDecimal("valor"));
                p.setCodigoTransacao(rs.getString("codigo_transacao"));
                p.setAprovado(rs.getBoolean("aprovado"));
                p.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                p.setAlunoNome(rs.getString("aluno_nome"));
                lista.add(p);
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar pagamentos: " + e.getMessage(), e);
        }
    }
}
