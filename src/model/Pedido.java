package model;

import decorator.Bebida;

public class Pedido {
    private final int numero;
    private final Bebida bebida;

    public Pedido(int numero, Bebida bebida) {
        this.numero = numero;
        this.bebida = bebida;
    }

    public int getNumero() { return numero; }
    public Bebida getBebida() { return bebida; }

    @Override
    public String toString() {
        return "#" + numero + " - " + bebida.getDescricao() + " = " + bebida.getPrecoFormatado();
    }
}
