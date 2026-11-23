package observer;

import model.Pedido;

/** Observador concreto 2: registra o valor. */
public class Caixa implements PedidoObserver {
    @Override
    public void aoCriarPedido(Pedido pedido) {
        System.out.println("[Caixa]   Registrar " + pedido.getBebida().getPrecoFormatado()
                + " (pedido #" + pedido.getNumero() + ")");
    }
}
