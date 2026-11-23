package observer;

import model.Pedido;

/** Observador concreto 1: prepara o pedido. */
public class Barista implements PedidoObserver {
    @Override
    public void aoCriarPedido(Pedido pedido) {
        System.out.println("[Barista] Preparar o pedido #" + pedido.getNumero()
                + ": " + pedido.getBebida().getDescricao());
    }
}
