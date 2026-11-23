package model;

import util.Moeda;
import java.math.BigDecimal;

public class Plano {
    private final int id;
    private final String nome;
    private final BigDecimal valorMensal;
    private final String descricao;

    public Plano(int id, String nome, BigDecimal valorMensal, String descricao) {
        this.id = id; this.nome = nome; this.valorMensal = valorMensal; this.descricao = descricao;
    }
    public int getId() { return id; }
    public String getNome() { return nome; }
    public BigDecimal getValorMensal() { return valorMensal; }
    public String getDescricao() { return descricao; }
    @Override public String toString() { return nome + " - " + Moeda.formatar(valorMensal) + "/mês"; }
}
