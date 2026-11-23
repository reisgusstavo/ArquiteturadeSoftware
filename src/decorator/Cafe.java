package decorator;

import java.math.BigDecimal;

/** Componente concreto: o café simples, sem nada. */
public class Cafe implements Bebida {
    @Override public String getDescricao() { return "Café"; }
    @Override public BigDecimal getPreco() { return new BigDecimal("5.00"); }
}
