package com.medeiros.pdf_generator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

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

    // Construtor padrão para serialização/deserialização JSON

    public Venda(Integer codigo, Cliente cliente, List<Produto> arrList) {
        this.codigo = codigo;
        this.dataVenda = LocalDate.now();
        this.cliente = cliente;
        this.produtosVendidos = arrList;
    }

    public String calcularValorTotalCarrinho() {
        double total = 0;
        for (Produto produto : produtosVendidos) {
            total += produto.calcularPreco();
        }
        BigDecimal valorFormatado = new BigDecimal(total).setScale(2, RoundingMode.HALF_DOWN);
        return valorFormatado.toString();
    }

    public void addProdutoAoCarrinho(Produto produto) {
        this.produtosVendidos.add(produto);
    }
}
