package dao;

import util.PersistenciaException;
import model.Aluno;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAOJdbc implements AlunoDAO {

    private Connection conn() { return ConnectionFactory.getInstance().getConnection(); }

    @Override
    public void salvar(Aluno a) {
        String sql = "INSERT INTO alunos (nome, cpf, email, telefone, data_nascimento, ativo, data_cadastro) "
                   + "VALUES (?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getCpf());
            ps.setString(3, vazioParaNulo(a.getEmail()));
            ps.setString(4, vazioParaNulo(a.getTelefone()));
            ps.setDate(5, a.getDataNascimento() == null ? null : Date.valueOf(a.getDataNascimento()));
            ps.setBoolean(6, a.isAtivo());
            ps.setTimestamp(7, Timestamp.valueOf(a.getDataCadastro()));
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) a.setId(keys.getInt(1));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void atualizar(Aluno a) {
        String sql = "UPDATE alunos SET nome=?, cpf=?, email=?, telefone=?, data_nascimento=?, ativo=? WHERE id=?";
        try (PreparedStatement ps = conn().prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getCpf());
            ps.setString(3, vazioParaNulo(a.getEmail()));
            ps.setString(4, vazioParaNulo(a.getTelefone()));
            ps.setDate(5, a.getDataNascimento() == null ? null : Date.valueOf(a.getDataNascimento()));
            ps.setBoolean(6, a.isAtivo());
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public void excluir(int id) {
        try (PreparedStatement ps = conn().prepareStatement("DELETE FROM alunos WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao excluir aluno: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Aluno> listarTodos() {
        List<Aluno> lista = new ArrayList<>();
        try (PreparedStatement ps = conn().prepareStatement("SELECT * FROM alunos ORDER BY nome");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar alunos: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Aluno> buscarPorCpf(String cpf) {
        try (PreparedStatement ps = conn().prepareStatement("SELECT * FROM alunos WHERE cpf=?")) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapear(rs)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao buscar aluno: " + e.getMessage(), e);
        }
    }

    private Aluno mapear(ResultSet rs) throws SQLException {
        Date nasc = rs.getDate("data_nascimento");
        Aluno a = new Aluno();
        a.setId(rs.getInt("id"));
        a.setNome(rs.getString("nome"));
        a.setCpf(rs.getString("cpf"));
        a.setEmail(rs.getString("email"));
        a.setTelefone(rs.getString("telefone"));
        a.setDataNascimento(nasc == null ? null : nasc.toLocalDate());
        a.setAtivo(rs.getBoolean("ativo"));
        a.setDataCadastro(rs.getTimestamp("data_cadastro").toLocalDateTime());
        return a;
    }

    private String vazioParaNulo(String s) { return s == null || s.isBlank() ? null : s.trim(); }
}
