package dao;

import model.Aluno;
import java.util.List;
import java.util.Optional;

public interface AlunoDAO {
    void salvar(Aluno aluno);
    void atualizar(Aluno aluno);
    void excluir(int id);
    List<Aluno> listarTodos();
    Optional<Aluno> buscarPorCpf(String cpf);
}
