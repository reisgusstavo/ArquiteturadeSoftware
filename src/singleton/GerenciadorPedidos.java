package singleton;

import decorator.Bebida;
import model.Pedido;
import observer.PedidoObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * PADRÃO SINGLETON: existe um único gerenciador em todo o programa, assim o
 * número do pedido nunca se repete e todos veem a mesma lista de pedidos.
 *
 * PADRÃO OBSERVER: também é o "Subject": guarda os observadores e avisa
 * todos quando um pedido é criado.
 */
public class GerenciadorPedidos {
    private static GerenciadorPedidos instancia;      // a única instância

    private final List<PedidoObserver> observadores = new ArrayList<>();
    private final List<Pedido> pedidos = new ArrayList<>();
    private int proximoNumero = 1;

    private GerenciadorPedidos() { }                  // construtor privado: ninguém dá "new"

    public static synchronized GerenciadorPedidos getInstance() {
        if (instancia == null) {
            instancia = new GerenciadorPedidos();
        }
        return instancia;
    }

    public void registrar(PedidoObserver observador) {
        observadores.add(observador);
    }

    public Pedido criarPedido(Bebida bebida) {
        Pedido pedido = new Pedido(proximoNumero++, bebida);
        pedidos.add(pedido);
        for (PedidoObserver o : observadores) {       // avisa todos os interessados
            o.aoCriarPedido(pedido);
        }
        return pedido;
    }

    public List<Pedido> getPedidos() {
        return Collections.unmodifiableList(pedidos);
    }
}
