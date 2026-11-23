package dao;

import util.PersistenciaException;
import model.Plano;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlanoDAOJdbc implements PlanoDAO {
    @Override
    public List<Plano> listarTodos() {
        List<Plano> lista = new ArrayList<>();
        try (PreparedStatement ps = ConnectionFactory.getInstance().getConnection()
                .prepareStatement("SELECT * FROM planos ORDER BY valor_mensal DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Plano(rs.getInt("id"), rs.getString("nome"),
                        rs.getBigDecimal("valor_mensal"), rs.getString("descricao")));
            }
            return lista;
        } catch (SQLException e) {
            throw new PersistenciaException("Erro ao listar planos: " + e.getMessage(), e);
        }
    }
}
