package observer;

import model.Pedido;

/** Observer: quem quer saber de um novo pedido implementa esta interface. */
public interface PedidoObserver {
    void aoCriarPedido(Pedido pedido);
}
