package controller;

import util.ResultadoPagamento;
import util.NegocioException;
import model.Aluno;
import model.Matricula;
import model.MetodoPagamento;
import model.Pagamento;
import model.Plano;
import pattern.decorator.ExtraTipo;
import pattern.decorator.PlanoBase;
import pattern.decorator.PlanoServico;
import pattern.observer.EventoMatricula;
import pattern.observer.MatriculaPublisher;
import dao.MatriculaDAO;
import dao.PagamentoDAO;
import dao.PlanoDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/** Orquestra o caso de uso "matricular": Decorator (preço) -> pagamento -> banco -> Observer. */
public class MatriculaController {
    private final MatriculaDAO matriculaDAO;
    private final PagamentoDAO pagamentoDAO;
    private final PlanoDAO planoDAO;
    private final MatriculaPublisher publisher;
    private final PagamentoController pagamentoController = new PagamentoController();

    public MatriculaController(MatriculaDAO matriculaDAO, PagamentoDAO pagamentoDAO,
                            PlanoDAO planoDAO, MatriculaPublisher publisher) {
        this.matriculaDAO = matriculaDAO;
        this.pagamentoDAO = pagamentoDAO;
        this.planoDAO = planoDAO;
        this.publisher = publisher;
    }

    /** DECORATOR em ação: empilha os extras escolhidos sobre o plano base. */
    public PlanoServico montarServico(Plano plano, List<ExtraTipo> extras) {
        PlanoServico servico = new PlanoBase(plano);
        for (ExtraTipo extra : extras) {
            servico = extra.aplicar(servico);
        }
        return servico;
    }

    public Matricula matricular(Aluno aluno, Plano plano, List<ExtraTipo> extras,
                                MetodoPagamento metodo, String dadosMeio) {
        if (aluno == null) throw new NegocioException("Selecione um aluno.");
        if (plano == null) throw new NegocioException("Selecione um plano.");
        if (!aluno.isAtivo()) throw new NegocioException("O aluno está inativo.");

        PlanoServico servico = montarServico(plano, extras);

        ResultadoPagamento resultado = pagamentoController.processar(metodo, servico.getValor(), dadosMeio);
        if (!resultado.aprovado())
            throw new NegocioException("Pagamento recusado: " + resultado.mensagem());

        Matricula m = new Matricula();
        m.setAlunoId(aluno.getId());
        m.setPlanoId(plano.getId());
        m.setExtras(extras.stream().map(ExtraTipo::toString).collect(Collectors.joining(", ")));
        m.setDescricao(servico.getDescricao());
        m.setValorTotal(servico.getValor());
        m.setDataInicio(LocalDate.now());

        Pagamento p = new Pagamento();
        p.setMetodo(metodo);
        p.setValor(servico.getValor());
        p.setCodigoTransacao(resultado.codigo());
        p.setAprovado(true);
        p.setDataHora(LocalDateTime.now());

        matriculaDAO.registrar(m, p);

        // OBSERVER em ação: avisa quem estiver inscrito (telas, log...).
        publisher.notificar(new EventoMatricula(aluno.getNome(), servico.getDescricao(),
                servico.getValor(), metodo, resultado.codigo()));
        return m;
    }

    public List<Plano> listarPlanos() { return planoDAO.listarTodos(); }
    public List<Matricula> listarMatriculas() { return matriculaDAO.listarTodas(); }
    public List<Pagamento> listarPagamentos() { return pagamentoDAO.listarTodos(); }
}
