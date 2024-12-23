package com.medeiros.pdf_generator.domain.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.medeiros.pdf_generator.domain.enums.FormaDePagamento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Venda {

    private Integer codigo;
    private LocalDate dataVenda;
    private Cliente cliente;
    private List<Produto> produtosVendidos;
    private FormaDePagamento formaDePagamento;
    private Double valorDaEntrada;
    private Double total;
    private String obs;

    public Venda(Integer codigo, Cliente cliente, List<Produto> arrList,
            FormaDePagamento formaDePagamento, Double valorDaEntrada, String obs) {
        this.codigo = codigo;
        this.dataVenda = LocalDate.now();
        this.cliente = cliente;
        this.produtosVendidos = arrList;
        this.formaDePagamento = formaDePagamento;
        this.valorDaEntrada = valorDaEntradaIsNull(valorDaEntrada);
        this.total = 0.0;
        this.obs = obs;

    }

    public Double valorDaEntradaIsNull(Double valorDaEntrada) {
        return Optional.ofNullable(valorDaEntrada).orElse(0.0);
    }

    public Double calcularValorTotalCarrinho() {
        double total = 0;
        for (Produto produto : produtosVendidos) {
            total += produto.calcularPreco();
        }
        BigDecimal valorFormatado = new BigDecimal(total).setScale(2, RoundingMode.HALF_DOWN);
        return valorFormatado.doubleValue();
    }

    public void addProdutoAoCarrinho(Produto produto) {
        this.produtosVendidos.add(produto);
        this.total = calcularValorTotalCarrinho() - this.valorDaEntrada;
    }
}
