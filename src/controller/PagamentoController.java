package controller;

import util.ResultadoPagamento;
import model.MetodoPagamento;
import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

/**
 * Processamento de pagamento SIMULADO (não há integração real).
 * Pix: sempre aprovado. Cartão: exige 16 dígitos; número terminado em 0000 simula saldo insuficiente.
 */
public class PagamentoController {

    public ResultadoPagamento processar(MetodoPagamento metodo, BigDecimal valor, String numeroCartao) {
        return switch (metodo) {
            case PIX -> ResultadoPagamento.aprovado("PIX" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            case CARTAO -> processarCartao(numeroCartao);
        };
    }

    private ResultadoPagamento processarCartao(String numero) {
        if (numero == null || !numero.matches("\\d{16}"))
            return ResultadoPagamento.recusado("Cartão inválido (informe 16 dígitos)");
        if (numero.endsWith("0000"))
            return ResultadoPagamento.recusado("Saldo insuficiente");
        return ResultadoPagamento.aprovado("NSU" + (100000 + new Random().nextInt(900000)));
    }
}
