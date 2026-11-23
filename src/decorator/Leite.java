package decorator;

import java.math.BigDecimal;

public class Leite extends Adicional {
    public Leite(Bebida bebida) { super(bebida); }
    @Override protected String nome() { return "Leite"; }
    @Override protected BigDecimal valor() { return new BigDecimal("2.00"); }
}
