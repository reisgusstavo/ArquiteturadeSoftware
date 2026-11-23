package decorator;

import java.math.BigDecimal;

/**
 * PADRÃO DECORATOR.
 * Um adicional "embrulha" outra Bebida (o café ou outro adicional) e soma
 * a sua descrição e o seu preço. Assim não precisamos criar uma classe
 * para cada combinação (CafeComLeite, CafeComLeiteEChantilly...).
 */
public abstract class Adicional implements Bebida {
    protected final Bebida bebida;

    protected Adicional(Bebida bebida) { this.bebida = bebida; }

    protected abstract String nome();
    protected abstract BigDecimal valor();

    @Override public String getDescricao() { return bebida.getDescricao() + " + " + nome(); }
    @Override public BigDecimal getPreco() { return bebida.getPreco().add(valor()); }
}
