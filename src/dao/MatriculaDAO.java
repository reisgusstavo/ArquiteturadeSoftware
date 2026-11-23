package dao;

import model.Matricula;
import model.Pagamento;
import java.util.List;

public interface MatriculaDAO {
    /** Grava matrícula e pagamento na mesma transação (ou grava os dois, ou nenhum). */
    void registrar(Matricula matricula, Pagamento pagamento);
    List<Matricula> listarTodas();
}
