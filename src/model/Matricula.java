package model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Matricula {
    private int id;
    private int alunoId;
    private int planoId;
    private String extras;
    private String descricao;
    private BigDecimal valorTotal;
    private LocalDate dataInicio;
    // campos de exibição (preenchidos por JOIN na listagem)
    private String alunoNome;
    private String planoNome;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }
    public int getPlanoId() { return planoId; }
    public void setPlanoId(int planoId) { this.planoId = planoId; }
    public String getExtras() { return extras; }
    public void setExtras(String extras) { this.extras = extras; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }
    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }
    public String getAlunoNome() { return alunoNome; }
    public void setAlunoNome(String alunoNome) { this.alunoNome = alunoNome; }
    public String getPlanoNome() { return planoNome; }
    public void setPlanoNome(String planoNome) { this.planoNome = planoNome; }
}
