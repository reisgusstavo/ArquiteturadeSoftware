package model;

public enum MetodoPagamento {
    PIX("Pix"), CARTAO("Cartão de crédito");

    private final String rotulo;
    MetodoPagamento(String rotulo) { this.rotulo = rotulo; }
    @Override public String toString() { return rotulo; }
}
