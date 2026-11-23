package decorator;

import java.math.BigDecimal;

public class Chocolate extends Adicional {
    public Chocolate(Bebida bebida) { super(bebida); }
    @Override protected String nome() { return "Chocolate"; }
    @Override protected BigDecimal valor() { return new BigDecimal("4.00"); }
}
