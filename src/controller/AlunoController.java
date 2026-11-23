package controller;

import util.NegocioException;
import model.Aluno;
import dao.AlunoDAO;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AlunoController {
    private final AlunoDAO dao;

    public AlunoController(AlunoDAO dao) { this.dao = dao; }

    public void cadastrar(Aluno aluno) {
        validar(aluno);
        if (dao.buscarPorCpf(aluno.getCpf()).isPresent())
            throw new NegocioException("Já existe um aluno cadastrado com este CPF.");
        dao.salvar(aluno);
    }

    public void atualizar(Aluno aluno) {
        validar(aluno);
        Optional<Aluno> outro = dao.buscarPorCpf(aluno.getCpf());
        if (outro.isPresent() && outro.get().getId() != aluno.getId())
            throw new NegocioException("Este CPF pertence a outro aluno.");
        dao.atualizar(aluno);
    }

    public void excluir(int id) { dao.excluir(id); }

    public List<Aluno> listar() { return dao.listarTodos(); }

    /** Valida e normaliza (nome sem espaços nas pontas, CPF só com dígitos). */
    private void validar(Aluno a) {
        if (a.getNome() == null || a.getNome().isBlank())
            throw new NegocioException("O nome é obrigatório.");
        String digitos = a.getCpf() == null ? "" : a.getCpf().replaceAll("\\D", "");
        if (digitos.length() != 11)
            throw new NegocioException("O CPF deve ter 11 dígitos.");
        if (a.getEmail() != null && !a.getEmail().isBlank()
                && !a.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$"))
            throw new NegocioException("E-mail inválido.");
        if (a.getDataNascimento() != null && a.getDataNascimento().isAfter(LocalDate.now()))
            throw new NegocioException("A data de nascimento não pode ser futura.");
        a.setNome(a.getNome().trim());
        a.setCpf(digitos);
    }
}
