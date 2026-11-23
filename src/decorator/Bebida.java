package decorator;

import java.math.BigDecimal;
import java.util.Locale;

/** Componente: tudo que pode ser servido tem descrição e preço. */
public interface Bebida {
    String getDescricao();
    BigDecimal getPreco();

    default String getPrecoFormatado() {
        return String.format(Locale.forLanguageTag("pt-BR"), "R$ %.2f", getPreco());
    }
}
