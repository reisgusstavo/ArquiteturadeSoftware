package decorator;

import java.math.BigDecimal;

public class Chantilly extends Adicional {
    public Chantilly(Bebida bebida) { super(bebida); }
    @Override protected String nome() { return "Chantilly"; }
    @Override protected BigDecimal valor() { return new BigDecimal("3.00"); }
}
